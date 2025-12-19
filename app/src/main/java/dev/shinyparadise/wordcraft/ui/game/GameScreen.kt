package dev.shinyparadise.wordcraft.ui.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.shinyparadise.wordcraft.data.AllLevelsProvider
import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.LevelType
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel
import dev.shinyparadise.wordcraft.model.level.WordGridLevel
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.WordBuildState
import dev.shinyparadise.wordcraft.model.state.WordGridState
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.booster.BoostersPanel
import dev.shinyparadise.wordcraft.ui.game.wordbuild.WordBuildGame
import dev.shinyparadise.wordcraft.ui.game.wordgrid.WordGridGame
import dev.shinyparadise.wordcraft.ui.game.wordle.WordleGame
import dev.shinyparadise.wordcraft.viewmodel.GameScreenViewModel

@Composable
fun GameScreen(
    levelId: Int,
    onFinish: (LevelResultArg) -> Unit
) {
    val level = remember(levelId) {
        AllLevelsProvider.getAllLevels().first { it.id == levelId }
    }

    val viewModel: GameScreenViewModel = viewModel(
        factory = GameScreenViewModel.Factory(level, LocalContext.current)
    )

    val state by viewModel.state.collectAsState()
    val result = state.result
    val levelState = state.levelState

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

    GameScaffold(
        title = "Уровень $levelId",
        onBack = {}
    ) {
        GameContent(state, viewModel)
    }
}

@Composable
fun GameContent(
    state: GameState,
    viewModel: GameScreenViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
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
        }
    }
}
