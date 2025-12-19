package dev.shinyparadise.wordcraft.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.shinyparadise.wordcraft.data.GameDataStore
import dev.shinyparadise.wordcraft.data.ProgressRepository
import dev.shinyparadise.wordcraft.model.level.Level
import dev.shinyparadise.wordcraft.model.level.LevelResult
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel
import dev.shinyparadise.wordcraft.model.level.WordGridLevel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel
import dev.shinyparadise.wordcraft.model.state.LevelState
import dev.shinyparadise.wordcraft.model.state.WordBuildState
import dev.shinyparadise.wordcraft.model.state.WordGridState
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.GameScreenState
import dev.shinyparadise.wordcraft.ui.game.GameAction
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val level: Level,
    private val progressRepository: ProgressRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        GameScreenState(
            level = level,
            levelState = createLevelState(level)
        )
    )

    private fun createLevelState(level: Level): LevelState {
        return when (level) {
            is WordGridLevel -> WordGridState.initial(level.id)
            is WordGuessLevel -> WordGuessState.initial(level.id, level.maxAttempts)
            is WordBuildLevel -> WordBuildState.initial(level.id)
        }
    }

    val state: StateFlow<GameScreenState> = _state

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.SubmitWord -> submitWord(action.word)
            GameAction.UseRevealLetter -> revealLetter()
            is GameAction.SelectWord -> selectWord(action.word)
            is GameAction.SubmitBuiltWord -> submitBuiltWord(action.word)
        }
    }

    private fun revealLetter() {
        val current = _state.value.levelState as WordGuessState

        if (current.isCompleted) return

        val unrevealed = (level as WordGuessLevel).targetWord.indices
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
        val level = level as WordGuessLevel

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

    private fun selectWord(word: String) {
        val current = _state.value.levelState as WordGridState
        val level = _state.value.level as WordGridLevel

        if (current.isCompleted) return
        if (!level.words.contains(word)) return
        if (current.foundWords.contains(word)) return

        val newFound = current.foundWords + word
        val completed = newFound.size == level.words.size

        val newState = current.copy(
            foundWords = newFound,
            isCompleted = completed
        )

        _state.value = _state.value.copy(
            levelState = newState,
            isFinished = completed,
            result = if (completed) LevelResult.WIN else null
        )
    }

    private fun submitBuiltWord(word: String) {
        val level = _state.value.level as WordBuildLevel
        val current = _state.value.levelState as WordBuildState

        if (current.isCompleted) return
        if (!level.words.contains(word)) return
        if (current.foundWords.contains(word)) return

        val newFound = current.foundWords + word
        val completed = newFound.size == level.words.size

        val newState = current.copy(
            foundWords = newFound,
            isCompleted = completed
        )

        _state.value = _state.value.copy(
            levelState = newState,
            isFinished = completed,
            result = if (completed) LevelResult.WIN else null
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
