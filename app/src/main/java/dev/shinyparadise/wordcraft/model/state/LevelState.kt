package dev.shinyparadise.wordcraft.model.state

sealed interface LevelState {
    val levelId: Int
    val isCompleted: Boolean
    val usedHints: Int
    val boosters: Map<String, Int>
}
