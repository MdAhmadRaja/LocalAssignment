package com.example.flamingo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.example.flamingo.analytics.AnalyticsLogger
import com.example.flamingo.ui.LoginScreen
import com.example.flamingo.ui.OtpScreen
import com.example.flamingo.ui.SessionScreen
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val analyticsLogger = AnalyticsLogger()
        analyticsLogger.init(this)
        setContent {
            var currentScreen by remember { mutableStateOf(0) }
            when (currentScreen) {
                0 -> {
                    LoginScreen(
                        onOtpSent = {
                            currentScreen = 1
                        }
                    )
                }
                1 -> {
                    OtpScreen(
                        onLoginSuccess = {
                            currentScreen = 2
                        }
                    )
                }
                2 -> {
                    SessionScreen(
                        onLogout = {
                            currentScreen = 0
                        }
                    )
                }
            }
        }
    }
}