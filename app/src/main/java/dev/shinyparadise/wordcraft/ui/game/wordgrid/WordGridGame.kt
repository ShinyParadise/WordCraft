package dev.shinyparadise.wordcraft.ui.game.wordgrid

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.dp
import dev.shinyparadise.wordcraft.model.level.WordGridLevel
import dev.shinyparadise.wordcraft.model.state.GridPos
import dev.shinyparadise.wordcraft.model.state.WordGridState
import dev.shinyparadise.wordcraft.ui.game.GameAction

@Composable
fun WordGridGame(
    level: WordGridLevel,
    state: WordGridState,
    onAction: (GameAction) -> Unit
) {
    val selectedPath = remember { mutableStateListOf<GridPos>() }
    val cellBounds = remember { mutableStateMapOf<GridPos, Rect>() }
    var gridOriginInRoot by remember { mutableStateOf(Offset.Zero) }

    fun cellAt(localToGrid: Offset): GridPos? {
        val rootPos = localToGrid + gridOriginInRoot
        return cellBounds.entries.firstOrNull { it.value.contains(rootPos) }?.key
    }

    fun isAdjacent(a: GridPos, b: GridPos): Boolean {
        val dr = kotlin.math.abs(a.row - b.row)
        val dc = kotlin.math.abs(a.col - b.col)
        return dr <= 1 && dc <= 1 && !(dr == 0 && dc == 0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Проведи по буквам, чтобы собрать слово",
            style = MaterialTheme.typography.titleMedium
        )

        Column(
            modifier = Modifier
                .onGloballyPositioned { coords ->
                    gridOriginInRoot = coords.positionInRoot()
                }
                .pointerInput(level.id) {
                    awaitEachGesture {
                        val down = awaitFirstDown(requireUnconsumed = false)
                        selectedPath.clear()
                        cellAt(down.position)?.let { selectedPath.add(it) }

                        while (true) {
                            val event = awaitPointerEvent()
                            val change = event.changes.firstOrNull() ?: break

                            val pos = cellAt(change.position)
                            if (pos != null) {
                                val last = selectedPath.lastOrNull()

                                if (last == null) {
                                    selectedPath.add(pos)
                                } else if (pos != last) {
                                    if (selectedPath.size >= 2 && pos == selectedPath[selectedPath.size - 2]) {
                                        selectedPath.removeAt(selectedPath.lastIndex)
                                    } else if (isAdjacent(pos, last) && !selectedPath.contains(pos)) {
                                        selectedPath.add(pos)
                                    }
                                }
                            }

                            change.consume()

                            if (change.changedToUpIgnoreConsumed()) {
                                if (selectedPath.isNotEmpty()) {
                                    onAction(GameAction.SubmitGridSelection(selectedPath.toList()))
                                }
                                selectedPath.clear()
                                break
                            }
                        }
                    }
                },
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            level.grid.forEachIndexed { rowIndex, row ->
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    row.forEachIndexed { colIndex, letter ->
                        val pos = GridPos(rowIndex, colIndex)

                        WordGridCell(
                            letter = letter,
                            isSelected = selectedPath.contains(pos),
                            isFound = state.foundCells.contains(pos),
                            modifier = Modifier.onGloballyPositioned { coords ->
                                cellBounds[pos] = coords.boundsInRoot()
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text("Найди слова:")

        level.words.forEach { word ->
            val found = state.foundWords.contains(word)
            Text(
                text = word,
                style = if (found)
                    MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary
                    )
                else
                    MaterialTheme.typography.bodyMedium
            )
        }
    }
}
