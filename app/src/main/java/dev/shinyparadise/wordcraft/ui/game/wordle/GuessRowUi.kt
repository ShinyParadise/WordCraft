package dev.shinyparadise.wordcraft.ui.game.wordle

import dev.shinyparadise.wordcraft.model.state.LetterResult

data class GuessRowUi(
    val letters: List<Char>,
    val results: List<LetterResult>
)
