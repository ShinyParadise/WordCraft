package dev.shinyparadise.wordcraft.data

import dev.shinyparadise.wordcraft.data.level.WordBuildLevels
import dev.shinyparadise.wordcraft.data.level.WordGridLevels
import dev.shinyparadise.wordcraft.model.level.Level
import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.WordGuessLevel

object AllLevelsProvider {

    fun getAllLevels(): List<Level> {
        return listOf(
            // WORD GUESS (1–7)
            WordGuessLevel(1, 1, LevelStatus.UNLOCKED, "APPLE"),
            WordGuessLevel(2, 1, LevelStatus.LOCKED, "PLANT"),
            WordGuessLevel(3, 2, LevelStatus.LOCKED, "BRAVE"),
            WordGuessLevel(4, 2, LevelStatus.LOCKED, "STONE"),
            WordGuessLevel(5, 3, LevelStatus.LOCKED, "LIGHT"),
            WordGuessLevel(6, 3, LevelStatus.LOCKED, "TRAIN"),
            WordGuessLevel(7, 4, LevelStatus.LOCKED, "WORLD"),

            // WORD GRID (8–14)
            *WordGridLevels.getLevels().toTypedArray(),

            // WORD BUILD (15–20)
            *WordBuildLevels.getLevels().toTypedArray()
        )
    }
}
