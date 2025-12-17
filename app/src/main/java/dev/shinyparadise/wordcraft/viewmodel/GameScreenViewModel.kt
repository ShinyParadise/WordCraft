package dev.shinyparadise.wordcraft.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.shinyparadise.wordcraft.data.GameDataStore
import dev.shinyparadise.wordcraft.data.ProgressRepository
import dev.shinyparadise.wordcraft.model.level.LevelResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.GameScreenState
import dev.shinyparadise.wordcraft.ui.game.GameAction
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val level: WordGuessLevel,
    private val progressRepository: ProgressRepository,
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
            GameAction.UseRevealLetter -> revealLetter()
        }
    }

    private fun revealLetter() {
        val current = _state.value.levelState as WordGuessState

        if (current.isCompleted) return

        val unrevealed = level.targetWord.indices
            .filter { it !in current.revealedIndexes }

        if (unrevealed.isEmpty()) return

        val indexToReveal = unrevealed.random()

        val newState = current.copy(
            revealedIndexes = current.revealedIndexes + indexToReveal,
            usedHints = current.usedHints + 1
        )

        _state.value = _state.value.copy(levelState = newState)
    }

    private fun submitWord(word: String) {
        val current = _state.value.levelState as WordGuessState

        if (current.isCompleted || _state.value.isFinished) return
        if (word.length != level.targetWord.length) return
        if (current.currentAttempt >= current.maxAttempts) return

        val newGuesses = current.guesses + word
        val isWin = word == level.targetWord
        val newAttempt = current.currentAttempt + 1
        val isLose = !isWin && newAttempt == current.maxAttempts

        val newLevelState = current.copy(
            guesses = newGuesses,
            currentAttempt = newAttempt,
            isCompleted = isWin
        )

        if (isWin) {
            viewModelScope.launch {
                progressRepository.saveLevelCompleted(level.id)
                progressRepository.saveHintsUsed(level.id, newLevelState.usedHints)
            }
        }

        _state.value = _state.value.copy(
            levelState = newLevelState,
            isFinished = isWin || isLose,
            result = when {
                isWin -> LevelResult.WIN
                isLose -> LevelResult.LOSE
                else -> null
            }
        )
    }

    class Factory(
        private val level: WordGuessLevel,
        private val context: Context,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val dataStore = GameDataStore(context)
            val repository = ProgressRepository(dataStore)
            return GameScreenViewModel(level, repository) as T
        }
    }
}
