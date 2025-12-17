package dev.shinyparadise.wordcraft.ui.result

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun ResultScreen(
    levelId: Int,
    onBackToMap: () -> Unit
) {
    Text("Результат уровня $levelId")
    Button(onClick = onBackToMap) {
        Text("На карту")
    }
}
