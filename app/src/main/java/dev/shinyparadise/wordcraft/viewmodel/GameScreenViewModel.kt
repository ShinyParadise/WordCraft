package dev.shinyparadise.wordcraft.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dev.shinyparadise.wordcraft.data.GameDataStore
import dev.shinyparadise.wordcraft.data.ProgressRepository
import dev.shinyparadise.wordcraft.model.booster.ExtraAttemptBooster
import dev.shinyparadise.wordcraft.model.booster.RevealLetterBooster
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
import dev.shinyparadise.wordcraft.ui.game.GameState
import dev.shinyparadise.wordcraft.ui.game.GameAction
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameScreenViewModel(
    private val level: Level,
    private val progressRepository: ProgressRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(
        GameState(
            level = level,
            levelState = createLevelState(level)
        )
    )

    private fun createLevelState(level: Level): LevelState {
        return when (level) {
            is WordGridLevel -> WordGridState.initial(level.id)
            is WordGuessLevel -> WordGuessState.initial(level.id, level.maxAttempts).copy(
                boosters = initialBoosters()
            )
            is WordBuildLevel -> WordBuildState.initial(level.id)
        }
    }

    val state: StateFlow<GameState> = _state

    fun onAction(action: GameAction) {
        when (action) {
            is GameAction.SubmitWord -> submitWord(action.word)
            GameAction.UseRevealLetter -> revealLetter()
            GameAction.UseExtraAttempt -> TODO()
            is GameAction.SelectWord -> selectWord(action.word)
            is GameAction.SubmitBuiltWord -> submitBuiltWord(action.word)
        }
    }

    private fun revealLetter() {
        val state = _state.value.levelState

        if (state !is WordGuessState) return

        val count = state.boosters[RevealLetterBooster.id] ?: return
        if (count <= 0) return

        val targetWord = (level as WordGuessLevel).targetWord

        val hiddenIndices = targetWord.indices
            .filterNot { state.revealedIndexes.contains(it) }

        if (hiddenIndices.isEmpty()) return

        val index = hiddenIndices.random()

        _state.update {
            it.copy(
                levelState = state.copy(
                    revealedIndexes = state.revealedIndexes + index,
                    boosters = state.boosters.toMutableMap().apply {
                        put(RevealLetterBooster.id, count - 1)
                    }
                )
            )
        }
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

    private fun initialBoosters(): Map<String, Int> {
        return mapOf(
            RevealLetterBooster.id to RevealLetterBooster.maxCount,
            ExtraAttemptBooster.id to ExtraAttemptBooster.maxCount
        )
    }

    class Factory(
        private val level: Level,
        private val context: Context,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val dataStore = GameDataStore(context)
            val repository = ProgressRepository(dataStore)
            return GameScreenViewModel(level, repository) as T
        }
    }
}
