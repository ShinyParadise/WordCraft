package dev.shinyparadise.wordcraft.ui.game

sealed interface GameAction {
    data class SubmitWord(val word: String) : GameAction
    data object UseRevealLetter : GameAction
    data object UseExtraAttempt : GameAction

    data class SubmitGridSelection(val path: List<dev.shinyparadise.wordcraft.model.state.GridPos>) : GameAction

    data class SubmitBuiltWord(val word: String) : GameAction
}
