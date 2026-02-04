package com.example.flamingo.data
class OtpManager {

    companion object {
        private const val OTP_LENGTH = 6
        private const val OTP_EXPIRY_MILLIS = 60_000L
        private const val MAX_ATTEMPTS = 3
    }

    private data class OtpEntry(
        val otp: String,
        val createdAtMillis: Long,
        var attempts: Int
    )

    private val otpStore: MutableMap<String, OtpEntry> = mutableMapOf()

    fun generateOtp(email: String): String {
        val otp = (100000..999999).random().toString()

        otpStore[email] = OtpEntry(
            otp = otp,
            createdAtMillis = System.currentTimeMillis(),
            attempts = 0
        )

        return otp
    }

    fun validateOtp(email: String, enteredOtp: String): OtpValidationResult {
        val entry = otpStore[email]
            ?: return OtpValidationResult.Failure("OTP not generated")

        if (isOtpExpired(entry)) {
            otpStore.remove(email)
            return OtpValidationResult.Failure("OTP expired")
        }

        if (entry.attempts >= MAX_ATTEMPTS) {
            return OtpValidationResult.Failure("Maximum attempts exceeded")
        }

        return if (entry.otp == enteredOtp) {
            otpStore.remove(email)
            OtpValidationResult.Success
        } else {
            entry.attempts++
            val remainingAttempts = MAX_ATTEMPTS - entry.attempts

            OtpValidationResult.Failure(
                message = "Invalid OTP. $remainingAttempts attempts left",
                remainingAttempts = remainingAttempts
            )
        }
    }

    fun getRemainingTimeSeconds(email: String): Int {
        val entry = otpStore[email] ?: return 0

        val elapsed = System.currentTimeMillis() - entry.createdAtMillis
        val remainingMillis = OTP_EXPIRY_MILLIS - elapsed

        return (remainingMillis / 1000).toInt().coerceAtLeast(0)
    }

    private fun isOtpExpired(entry: OtpEntry): Boolean {
        return System.currentTimeMillis() - entry.createdAtMillis > OTP_EXPIRY_MILLIS
    }
}

sealed class OtpValidationResult {
    object Success : OtpValidationResult()
    data class Failure(
        val message: String,
        val remainingAttempts: Int = 0
    ) : OtpValidationResult()
}
