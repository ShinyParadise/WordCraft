package dev.shinyparadise.wordcraft.ui.game

import dev.shinyparadise.wordcraft.model.level.Level
import dev.shinyparadise.wordcraft.model.state.LevelState

data class GameScreenState(
    val level: Level,
    val levelState: LevelState,
)
