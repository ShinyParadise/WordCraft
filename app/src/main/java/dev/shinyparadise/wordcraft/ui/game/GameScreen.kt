package dev.shinyparadise.wordcraft.ui.game

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GameScreen(
    levelId: Int,
    onFinish: () -> Unit
) {
    Text("Игра. Уровень $levelId")
    Button(onClick = onFinish) {
        Text("Завершить уровень")
    }
}
