package dev.shinyparadise.wordcraft.ui.game.wordgrid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shinyparadise.wordcraft.model.level.WordGridLevel
import dev.shinyparadise.wordcraft.model.state.WordGridState
import dev.shinyparadise.wordcraft.ui.game.GameAction

@Composable
fun WordGridGame(
    level: WordGridLevel,
    state: WordGridState,
    onAction: (GameAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        WordGrid(level.grid)

        Text("Найди слова:")

        level.words.forEach { word ->
            val found = state.foundWords.contains(word)
            Text(
                text = word,
                modifier = Modifier.clickable(enabled = !found) {
                    onAction(GameAction.SelectWord(word))
                },
                style = if (found)
                    MaterialTheme.typography.bodyMedium.copy(
                        color = androidx.compose.ui.graphics.Color.Green
                    )
                else
                    MaterialTheme.typography.bodyMedium
            )
        }
    }
}
