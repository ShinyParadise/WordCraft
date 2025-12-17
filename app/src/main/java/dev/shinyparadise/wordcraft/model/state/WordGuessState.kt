package dev.shinyparadise.wordcraft.model.state

data class WordGuessState(
    override val levelId: Int,
    override val isCompleted: Boolean,
    override val usedHints: Int,
    override val maxAttempts: Int,
    override val currentAttempt: Int,
    val guesses: List<String>,
    val revealedIndexes: Set<Int> = emptySet()
) : LevelState, AttemptBasedState {

    companion object {
        fun initial(levelId: Int, maxAttempts: Int): WordGuessState {
            return WordGuessState(
                levelId = levelId,
                isCompleted = false,
                usedHints = 0,
                maxAttempts = maxAttempts,
                currentAttempt = 0,
                guesses = emptyList(),
                revealedIndexes = emptySet()
            )
        }
    }
}
