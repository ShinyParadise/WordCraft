package dev.shinyparadise.wordcraft.model.level

sealed interface Level {
    val id: Int
    val type: LevelType
    val difficulty: Int
    val status: LevelStatus
}

data class WordGuessLevel(
    override val id: Int,
    override val difficulty: Int,
    override val status: LevelStatus,
    val targetWord: String,
    val maxAttempts: Int = 5
) : Level {
    override val type: LevelType = LevelType.WORD_GUESS
}

