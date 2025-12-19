package dev.shinyparadise.wordcraft.ui.game.wordbuild

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel
import dev.shinyparadise.wordcraft.model.state.WordBuildState
import dev.shinyparadise.wordcraft.ui.game.GameAction

@Composable
fun WordBuildGame(
    level: WordBuildLevel,
    state: WordBuildState,
    onAction: (GameAction) -> Unit
) {
    var input by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            level.letters.forEach { letter ->
                LetterChip(letter)
            }
        }

        OutlinedTextField(
            value = input,
            onValueChange = { input = it.uppercase() },
            label = { Text("Собери слово") }
        )

        Button(onClick = {
            onAction(GameAction.SubmitBuiltWord(input))
            input = ""
        }) {
            Text("Проверить")
        }

        Text("Найденные слова:")

        state.foundWords.forEach {
            Text(it, color = androidx.compose.ui.graphics.Color.Green)
        }
    }
}
