package dev.shinyparadise.wordcraft.ui.game.wordbuild

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel
import dev.shinyparadise.wordcraft.model.state.WordBuildState
import dev.shinyparadise.wordcraft.ui.game.GameAction
import kotlin.random.Random

@Composable
fun WordBuildGame(
    level: WordBuildLevel,
    state: WordBuildState,
    onAction: (GameAction) -> Unit
) {
    var input by remember { mutableStateOf("") }
    val maxLen = level.letters.size
    val shuffledLetters = remember(level.id, level.letters) {
        level.letters.shuffled(Random(level.id))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            shuffledLetters.forEach { letter ->
                LetterChip(letter)
            }
        }

        OutlinedTextField(
            value = input,
            onValueChange = {
                val normalized = it.trim().uppercase()
                if (normalized.length <= maxLen) {
                    input = normalized
                }
            },
            label = { Text("Собери слово") }
            ,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                imeAction = ImeAction.Done
            )
        )

        Button(
            enabled = input.isNotBlank(),
            onClick = {
                onAction(GameAction.SubmitBuiltWord(input))
                input = ""
            }
        ) {
            Text("Проверить")
        }

        Text("Найденные слова: ${state.foundWords.size}/${level.words.size}")

        state.foundWords.forEach {
            Text(it, color = MaterialTheme.colorScheme.primary)
        }
    }
}
