package com.example.flamingo.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flamingo.viewmodel.AuthViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SessionScreen(
    authViewModel: AuthViewModel = viewModel(),
    onLogout: () -> Unit
) {
    val state by authViewModel.uiState.collectAsState()
    val startTimeFormatted = rememberFormattedTime(state.sessionStartTimeMillis)
    val durationFormatted = formatDuration(state.sessionDurationSeconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Session Started At",
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = startTimeFormatted,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Session Duration",
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Text(
            text = durationFormatted,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = {
                authViewModel.logout()
                onLogout()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout")
        }
    }
}

@Composable
private fun rememberFormattedTime(timeMillis: Long): String {
    if (timeMillis == 0L) return "--:--"

    val formatter = SimpleDateFormat("hh:mm:ss a", Locale.getDefault())
    return formatter.format(Date(timeMillis))
}

private fun formatDuration(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d", minutes, remainingSeconds)
}
