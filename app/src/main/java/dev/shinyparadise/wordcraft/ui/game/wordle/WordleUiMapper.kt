package dev.shinyparadise.wordcraft.ui.game.wordle

import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.LetterResult
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.model.state.WordleEvaluator

object WordleUiMapper {

    fun map(
        level: WordGuessLevel,
        state: WordGuessState
    ): List<GuessRowUi> {

        val rows = mutableListOf<GuessRowUi>()

        state.guesses.forEach { guess ->
            rows += GuessRowUi(
                letters = guess.toList(),
                results = WordleEvaluator.evaluateGuess(
                    guess = guess,
                    target = level.targetWord
                )
            )
        }

        val emptyRows = level.maxAttempts - rows.size
        repeat(emptyRows) {
            rows += GuessRowUi(
                letters = List(level.targetWord.length) { ' ' },
                results = List(level.targetWord.length) { LetterResult.ABSENT }
            )
        }

        return rows
    }
}
