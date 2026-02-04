package com.example.flamingo.analytics
import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
class AnalyticsLogger {
    private var firebaseAnalytics: FirebaseAnalytics? = null
    fun init(context: Context) {
        if (firebaseAnalytics == null) {
            firebaseAnalytics = FirebaseAnalytics.getInstance(context)
        }
    }

    fun logOtpGenerated() {
        firebaseAnalytics?.logEvent(
            EVENT_OTP_GENERATED,
            Bundle()
        )
    }

    fun logOtpValidationSuccess() {
        firebaseAnalytics?.logEvent(
            EVENT_OTP_VALIDATION_SUCCESS,
            Bundle()
        )
    }

    fun logOtpValidationFailure() {
        firebaseAnalytics?.logEvent(
            EVENT_OTP_VALIDATION_FAILURE,
            Bundle()
        )
    }

    fun logLogout() {
        firebaseAnalytics?.logEvent(
            EVENT_LOGOUT,
            Bundle()
        )
    }

    companion object {
        private const val EVENT_OTP_GENERATED = "otp_generated"
        private const val EVENT_OTP_VALIDATION_SUCCESS = "otp_validation_success"
        private const val EVENT_OTP_VALIDATION_FAILURE = "otp_validation_failure"
        private const val EVENT_LOGOUT = "logout"
    }
}
