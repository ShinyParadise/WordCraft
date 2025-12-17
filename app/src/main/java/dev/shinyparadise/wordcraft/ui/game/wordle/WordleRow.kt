package dev.shinyparadise.wordcraft.ui.game.wordle

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordleRow(row: GuessRowUi) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        row.letters.zip(row.results).forEach { (letter, result) ->
            WordleCell(
                letter = letter,
                result = result
            )
        }
    }
}
