package dev.shinyparadise.wordcraft.ui.game

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.LevelType
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel
import dev.shinyparadise.wordcraft.model.level.WordGridLevel
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.WordBuildState
import dev.shinyparadise.wordcraft.model.state.WordGridState
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.wordbuild.WordBuildGame
import dev.shinyparadise.wordcraft.ui.game.wordgrid.WordGridGame
import dev.shinyparadise.wordcraft.ui.game.wordle.WordleGame
import dev.shinyparadise.wordcraft.viewmodel.GameScreenViewModel

@Composable
fun GameScreen(
    levelId: Int,
    onFinish: (LevelResultArg) -> Unit
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
        factory = GameScreenViewModel.Factory(level, LocalContext.current)
    )

    val state by viewModel.state.collectAsState()
    val result = state.result

    LaunchedEffect(state.isFinished) {
        if (state.isFinished && result != null) {
            onFinish(
                LevelResultArg(
                    levelId = levelId,
                    result = result
                )
            )
        }
    }

    when (state.level.type) {
        LevelType.WORD_GUESS -> {
            WordleGame(
                level = state.level as WordGuessLevel,
                state = state.levelState as WordGuessState,
                onAction = viewModel::onAction
            )
        }
        LevelType.WORD_GRID -> {
            WordGridGame(
                level = state.level as WordGridLevel,
                state = state.levelState as WordGridState,
                onAction = viewModel::onAction
            )
        }
        LevelType.WORD_BUILD -> {
            WordBuildGame(
                level = state.level as WordBuildLevel,
                state = state.levelState as WordBuildState,
                onAction = viewModel::onAction
            )
        }
        else -> Unit
    }
}
