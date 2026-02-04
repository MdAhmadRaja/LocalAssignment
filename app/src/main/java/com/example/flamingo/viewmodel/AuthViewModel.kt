package com.example.flamingo.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flamingo.analytics.AnalyticsLogger
import com.example.flamingo.data.OtpManager
import com.example.flamingo.data.OtpValidationResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
class AuthViewModel(
    private val otpManager: OtpManager = OtpManager(),
    private val analyticsLogger: AnalyticsLogger = AnalyticsLogger()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthState())
    val uiState: StateFlow<AuthState> = _uiState
    private var otpTimerJob: Job? = null
    private var sessionTimerJob: Job? = null
    fun onEmailChanged(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onOtpChanged(otp: String) {
        _uiState.update { it.copy(enteredOtp = otp) }
    }
    fun sendOtp() {
        val email = uiState.value.email
        if (email.isBlank()) return

        val otp = otpManager.generateOtp(email)
        analyticsLogger.logOtpGenerated()
        startOtpTimer(email)

        _uiState.update {
            it.copy(
                isOtpSent = true,
                otpError = null,
                remainingAttempts = 3,
                debugOtp = otp
            )
        }
    }

    fun verifyOtp() {
        val state = uiState.value
        val email = state.email

        val result = otpManager.validateOtp(email, state.enteredOtp)

        when (result) {
            is OtpValidationResult.Success -> {
                analyticsLogger.logOtpValidationSuccess()
                startSession()
            }
            is OtpValidationResult.Failure -> {
                analyticsLogger.logOtpValidationFailure()
                _uiState.update {
                    it.copy(
                        otpError = result.message,
                        remainingAttempts = result.remainingAttempts
                    )
                }
            }
        }
    }

    fun resendOtp() {
        val otp = otpManager.generateOtp(uiState.value.email)
        analyticsLogger.logOtpGenerated()
        startOtpTimer(uiState.value.email)

        _uiState.update {
            it.copy(
                otpError = null,
                remainingAttempts = 3,
                debugOtp = otp
            )
        }
    }

    private fun startOtpTimer(email: String) {
        otpTimerJob?.cancel()

        otpTimerJob = viewModelScope.launch {
            while (true) {
                val remaining = otpManager.getRemainingTimeSeconds(email)

                _uiState.update {
                    it.copy(remainingTimeSeconds = remaining)
                }
                if (remaining <= 0) break
                delay(1000)
            }
        }
    }
    private fun startSession() {
        otpTimerJob?.cancel()
        val startTime = System.currentTimeMillis()
        _uiState.update {
            it.copy(
                isAuthenticated = true,
                sessionStartTimeMillis = startTime,
                sessionDurationSeconds = 0,
                enteredOtp = "",
                otpError = null,
                debugOtp = null
            )
        }
        startSessionTimer(startTime)
    }

    private fun startSessionTimer(startTime: Long) {
        sessionTimerJob?.cancel()
        sessionTimerJob = viewModelScope.launch {
            while (true) {
                val durationSeconds =
                    (System.currentTimeMillis() - startTime) / 1000

                _uiState.update {
                    it.copy(sessionDurationSeconds = durationSeconds)
                }
                delay(1000)
            }
        }
    }

    fun logout() {
        sessionTimerJob?.cancel()
        analyticsLogger.logLogout()
        _uiState.value = AuthState()
    }

    override fun onCleared() {
        otpTimerJob?.cancel()
        sessionTimerJob?.cancel()
        super.onCleared()
    }
}
