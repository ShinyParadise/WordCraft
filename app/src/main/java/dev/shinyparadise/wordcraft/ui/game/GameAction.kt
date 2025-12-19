package dev.shinyparadise.wordcraft.ui.game

sealed interface GameAction {
    data class SubmitWord(val word: String) : GameAction
    data object UseRevealLetter : GameAction
    data object UseExtraAttempt : GameAction

    data class SelectWord(val word: String) : GameAction

    data class SubmitBuiltWord(val word: String) : GameAction
}
