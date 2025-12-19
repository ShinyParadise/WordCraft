package dev.shinyparadise.wordcraft.ui.game.wordgrid

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun WordGrid(grid: List<List<Char>>) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        grid.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                row.forEach { letter ->
                    WordGridCell(
                        letter = letter,
                        isSelected = false,
                        isFound = false
                    )
                }
            }
        }
    }
}
