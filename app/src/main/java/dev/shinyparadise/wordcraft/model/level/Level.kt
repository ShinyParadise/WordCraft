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

data class WordGridLevel(
    override val id: Int,
    override val difficulty: Int,
    override val status: LevelStatus,
    val grid: List<List<Char>>,
    val words: List<String>
) : Level {

    override val type: LevelType = LevelType.WORD_GRID
}

data class WordBuildLevel(
    override val id: Int,
    override val difficulty: Int,
    override val status: LevelStatus,
    val letters: List<Char>,
    val words: List<String>
) : Level {

    override val type: LevelType = LevelType.WORD_BUILD
}
