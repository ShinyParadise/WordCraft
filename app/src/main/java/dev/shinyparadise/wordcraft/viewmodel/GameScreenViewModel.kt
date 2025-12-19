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
import dev.shinyparadise.wordcraft.model.state.GridPos
import dev.shinyparadise.wordcraft.model.state.LevelState
import dev.shinyparadise.wordcraft.model.state.WordBuildState
import dev.shinyparadise.wordcraft.model.state.WordGridState
import dev.shinyparadise.wordcraft.model.state.WordGuessState
import dev.shinyparadise.wordcraft.ui.game.GameState
import dev.shinyparadise.wordcraft.ui.game.GameAction
import kotlin.math.abs
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
            GameAction.UseExtraAttempt -> useExtraAttempt()
            is GameAction.SubmitGridSelection -> submitGridSelection(action.path)
            is GameAction.SubmitBuiltWord -> submitBuiltWord(action.word)
        }
    }

    private fun revealLetter() {
        val state = _state.value.levelState

        if (state !is WordGuessState) return

        val count = state.boosters[RevealLetterBooster.id] ?: return
        if (count <= 0) return

        val level = level as WordGuessLevel
        val targetWord = level.targetWord

        val hiddenIndices = targetWord.indices
            .filterNot { state.revealedIndexes.contains(it) }

        if (hiddenIndices.isEmpty()) return

        val index = hiddenIndices.random()

        _state.update {
            it.copy(
                levelState = state.copy(
                    revealedIndexes = state.revealedIndexes + index,
                    usedHints = state.usedHints + 1,
                    boosters = state.boosters.toMutableMap().apply {
                        put(RevealLetterBooster.id, count - 1)
                    }
                )
            )
        }
    }

    private fun useExtraAttempt() {
        val state = _state.value.levelState
        if (state !is WordGuessState) return
        if (state.isCompleted || _state.value.isFinished) return

        val count = state.boosters[ExtraAttemptBooster.id] ?: return
        if (count <= 0) return

        _state.update {
            it.copy(
                levelState = state.copy(
                    maxAttempts = state.maxAttempts + 1,
                    usedHints = state.usedHints + 1,
                    boosters = state.boosters.toMutableMap().apply {
                        put(ExtraAttemptBooster.id, count - 1)
                    }
                )
            )
        }
    }

    private fun submitWord(word: String) {
        val current = _state.value.levelState as WordGuessState
        val level = level as WordGuessLevel
        val targetWord = level.targetWord

        val normalized = word.trim().uppercase()

        if (current.isCompleted || _state.value.isFinished) return
        if (normalized.length != targetWord.length) return
        if (current.currentAttempt >= current.maxAttempts) return

        val newGuesses = current.guesses + normalized
        val isWin = normalized == targetWord
        val newAttempt = current.currentAttempt + 1
        val isLose = !isWin && newAttempt == current.maxAttempts

        if (isWin) {
            val newLevelState = current.copy(
                guesses = newGuesses,
                currentAttempt = newAttempt,
                isCompleted = true
            )

            viewModelScope.launch {
                progressRepository.saveLevelCompleted(level.id)
                progressRepository.saveHintsUsed(level.id, newLevelState.usedHints)
                if (level.id < 20) {
                    progressRepository.tryUnlockLevel(level.id + 1)
                }
            }

            _state.value = _state.value.copy(
                levelState = newLevelState,
                isFinished = true,
                result = LevelResult.WIN
            )
            return
        }

        val newLevelState = current.copy(
            guesses = newGuesses,
            currentAttempt = newAttempt,
            isCompleted = false
        )

        _state.value = _state.value.copy(
            levelState = newLevelState,
            isFinished = isLose,
            result = if (isLose) LevelResult.LOSE else null
        )
    }

    private fun submitGridSelection(path: List<GridPos>) {
        val current = _state.value.levelState as WordGridState
        val level = _state.value.level as WordGridLevel

        if (current.isCompleted) return
        if (!isStraightContiguousPath(path)) return

        val word = buildString {
            path.forEach { pos ->
                val row = level.grid.getOrNull(pos.row) ?: return@forEach
                val ch = row.getOrNull(pos.col) ?: return@forEach
                append(ch)
            }
        }.trim().uppercase()

        if (word.isEmpty()) return

        val reversed = word.reversed()
        val matched = when {
            level.words.contains(word) -> word
            level.words.contains(reversed) -> reversed
            else -> return
        }

        if (current.foundWords.contains(matched)) return

        val newFoundWords = current.foundWords + matched
        val newFoundCells = current.foundCells + path.toSet()
        val completed = newFoundWords.size == level.words.size

        val newState = current.copy(
            foundWords = newFoundWords,
            foundCells = newFoundCells,
            isCompleted = completed
        )

        if (completed) {
            viewModelScope.launch {
                progressRepository.saveLevelCompleted(level.id)
                if (level.id < 20) {
                    progressRepository.tryUnlockLevel(level.id + 1)
                }
            }
        }

        _state.value = _state.value.copy(
            levelState = newState,
            isFinished = completed,
            result = if (completed) LevelResult.WIN else null
        )
    }

    private fun isStraightContiguousPath(path: List<GridPos>): Boolean {
        if (path.size < 2) return false

        // Если пользователь водит "туда-сюда" и попадает в одну клетку несколько раз —
        // это не засчитываем как корректное выделение слова.
        if (path.distinct().size != path.size) return false

        val first = path.first()
        val sameRow = path.all { it.row == first.row }
        val sameCol = path.all { it.col == first.col }
        if (!sameRow && !sameCol) return false

        for (i in 1 until path.size) {
            val a = path[i - 1]
            val b = path[i]
            val dr = abs(b.row - a.row)
            val dc = abs(b.col - a.col)
            if (dr + dc != 1) return false
        }

        return true
    }

    private fun submitBuiltWord(word: String) {
        val level = _state.value.level as WordBuildLevel
        val current = _state.value.levelState as WordBuildState

        val normalized = word.trim().uppercase()

        if (current.isCompleted) return
        if (normalized.isEmpty()) return
        if (!level.words.contains(normalized)) return
        if (!canBuildWord(normalized, level.letters)) return
        if (current.foundWords.contains(normalized)) return

        val newFound = current.foundWords + normalized
        val completed = newFound.size == level.words.size

        val newState = current.copy(
            foundWords = newFound,
            isCompleted = completed
        )

        if (completed) {
            viewModelScope.launch {
                progressRepository.saveLevelCompleted(level.id)
                if (level.id < 20) {
                    progressRepository.tryUnlockLevel(level.id + 1)
                }
            }
        }

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

    private fun canBuildWord(word: String, letters: List<Char>): Boolean {
        val available = letters.groupingBy { it }.eachCount().toMutableMap()
        for (c in word) {
            val left = available[c] ?: 0
            if (left <= 0) return false
            available[c] = left - 1
        }
        return true
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
