package dev.shinyparadise.wordcraft.ui.map

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*

@Composable
fun LevelMapScreen(
    onLevelSelected: (Int) -> Unit
) {
    Column {
        repeat(5) { index ->
            Button(
                onClick = { onLevelSelected(index + 1) }
            ) {
                Text("Уровень ${index + 1}")
            }
        }
    }
}
