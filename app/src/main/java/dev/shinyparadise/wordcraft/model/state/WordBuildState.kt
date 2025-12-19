package dev.shinyparadise.wordcraft.model.state

data class WordBuildState(
    override val levelId: Int,
    override val isCompleted: Boolean,
    override val usedHints: Int,
    val foundWords: Set<String>
) : LevelState {

    companion object {
        fun initial(levelId: Int): WordBuildState {
            return WordBuildState(
                levelId = levelId,
                isCompleted = false,
                usedHints = 0,
                foundWords = emptySet()
            )
        }
    }
}
