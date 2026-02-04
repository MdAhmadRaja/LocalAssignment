package com.example.flamingo.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flamingo.viewmodel.AuthViewModel

@Composable
fun OtpScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val state by authViewModel.uiState.collectAsState()
    LaunchedEffect(state.isAuthenticated) {
        if (state.isAuthenticated) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Enter OTP",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = state.enteredOtp,
            onValueChange = {
                if (it.length <= 6) {
                    authViewModel.onOtpChanged(it)
                }
            },
            label = { Text("6-digit OTP") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Time remaining: ${state.remainingTimeSeconds}s",
            color = if (state.remainingTimeSeconds > 0) Color.Black else Color.Red
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Attempts left: ${state.remainingAttempts}",
            color = if (state.remainingAttempts > 0) Color.Black else Color.Red
        )

        state.otpError?.let { error ->
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                color = Color.Red
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { authViewModel.verifyOtp() },
            enabled = state.enteredOtp.length == 6 &&
                    state.remainingAttempts > 0 &&
                    state.remainingTimeSeconds > 0,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Verify OTP")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { authViewModel.resendOtp() },
            enabled = state.remainingTimeSeconds <= 0 ||
                    state.remainingAttempts <= 0,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Resend OTP")
        }


        state.debugOtp?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "DEBUG OTP: $it",
                color = Color.Gray
            )
        }
    }
}
