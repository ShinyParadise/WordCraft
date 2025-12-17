package dev.shinyparadise.wordcraft.navigation

sealed interface Screen {
    data object MainMenu : Screen
    data object LevelMap : Screen
    data class Game(val levelId: Int) : Screen
    data class Result(val levelId: Int) : Screen
}
