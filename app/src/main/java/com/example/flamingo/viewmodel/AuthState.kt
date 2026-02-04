package com.example.flamingo.viewmodel
data class AuthState(
    val email: String = "",
    val enteredOtp: String = "",
    val isOtpSent: Boolean = false,
    val otpError: String? = null,
    val remainingAttempts: Int = 3,
    val otpExpiryTimeMillis: Long = 0L,
    val remainingTimeSeconds: Int = 0,
    val isAuthenticated: Boolean = false,
    val debugOtp: String? = null,
    val sessionStartTimeMillis: Long = 0L,
    val sessionDurationSeconds: Long = 0L,
    val isLoading: Boolean = false
)