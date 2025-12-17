package dev.shinyparadise.wordcraft.ui.game

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.LevelType
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.wordle.WordleGame
import dev.shinyparadise.wordcraft.viewmodel.GameScreenViewModel

@Composable
fun GameScreen(
    levelId: Int,
    onFinish: () -> Unit
) {
    val level = remember {
        WordGuessLevel(
            id = levelId,
            difficulty = 1,
            status = LevelStatus.UNLOCKED,
            targetWord = "APPLE"
        )
    }

    val viewModel: GameScreenViewModel = viewModel(
        factory = GameScreenViewModel.Factory(level)
    )

    val state by viewModel.state.collectAsState()

    when (state.level.type) {
        LevelType.WORD_GUESS -> {
            WordleGame(
                level = state.level as WordGuessLevel,
                state = state.levelState as WordGuessState,
                onAction = viewModel::onAction
            )
        }
        else -> Unit
    }
}
