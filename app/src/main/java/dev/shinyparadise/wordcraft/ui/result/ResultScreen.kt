package dev.shinyparadise.wordcraft.ui.result

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResultScreen(
    levelId: Int,
    isWin: Boolean,
    onBackToMap: () -> Unit,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isWin) "Уровень пройден!" else "Попробуй ещё раз",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onBackToMap) {
            Text("На карту уровней")
        }

        if (isWin && levelId < 20) {
            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onContinue) {
                Text("Продолжить")
            }
        }
    }
}
