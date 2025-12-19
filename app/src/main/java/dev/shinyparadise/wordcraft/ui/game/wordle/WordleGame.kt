package dev.shinyparadise.wordcraft.ui.game.wordle

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.GameAction

@Composable
fun WordleGame(
    level: WordGuessLevel,
    state: WordGuessState,
    onAction: (GameAction) -> Unit
) {
    var input by remember { mutableStateOf("") }

    val rows = remember(level, state) {
        WordleUiMapper.map(level, state)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        rows.forEach { row ->
            WordleRow(row)
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = input,
            onValueChange = {
                if (it.length <= level.targetWord.length) {
                    input = it.uppercase()
                }
            },
            label = { Text("Введите слово") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Characters,
                imeAction = ImeAction.Done
            )
        )

        Button(
            enabled = input.length == level.targetWord.length,
            onClick = {
                onAction(GameAction.SubmitWord(input))
                input = ""
            }
        ) {
            Text("Проверить")
        }
    }
}
