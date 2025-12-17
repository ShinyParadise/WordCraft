package dev.shinyparadise.wordcraft.ui.game

sealed interface GameAction {
    data class SubmitWord(val word: String) : GameAction
    data object UseRevealLetter : GameAction
}
