package dev.shinyparadise.wordcraft.data.level

import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel

object WordBuildLevels {

    fun getLevels(): List<WordBuildLevel> {
        return listOf(
            WordBuildLevel(
                id = 11,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                letters = listOf('C','A','T'),
                words = listOf("CAT", "AT")
            ),
            WordBuildLevel(
                id = 12,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                letters = listOf('D','O','G'),
                words = listOf("DOG", "GO")
            ),
            WordBuildLevel(
                id = 13,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                letters = listOf('F','I','R','E'),
                words = listOf("FIRE", "IF", "RE")
            ),
            WordBuildLevel(
                id = 14,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                letters = listOf('W','A','T','E','R'),
                words = listOf("WATER", "TEAR", "RATE")
            ),
            WordBuildLevel(
                id = 15,
                difficulty = 3,
                status = LevelStatus.LOCKED,
                letters = listOf('P','L','A','N','T'),
                words = listOf("PLANT", "ANT", "LAP")
            )
        )
    }
}
