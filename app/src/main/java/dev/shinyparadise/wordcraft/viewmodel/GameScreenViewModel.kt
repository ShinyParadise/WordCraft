package dev.shinyparadise.wordcraft.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.GameScreenState
import dev.shinyparadise.wordcraft.ui.game.GameAction

class GameScreenViewModel(
    level: WordGuessLevel
) : ViewModel() {

    private val _state = MutableStateFlow(
        GameScreenState(
            level = level,
            levelState = WordGuessState.initial(
                levelId = level.id,
                maxAttempts = level.maxAttempts
            )
        )
    )

    val state: StateFlow<GameScreenState> = _state

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.SubmitWord -> submitWord(action.word)
        }
    }

    private fun submitWord(word: String) {
        val level = _state.value.level as WordGuessLevel
        val current = _state.value.levelState as WordGuessState

        if (current.isCompleted) return
        if (word.length != level.targetWord.length) return
        if (current.currentAttempt >= current.maxAttempts) return

        val newGuesses = current.guesses + word
        val completed = word == level.targetWord

        val newState = current.copy(
            guesses = newGuesses,
            currentAttempt = current.currentAttempt + 1,
            isCompleted = completed
        )

        _state.value = _state.value.copy(levelState = newState)
    }

    class Factory(
        private val level: WordGuessLevel
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GameScreenViewModel(level) as T
        }
    }
}
