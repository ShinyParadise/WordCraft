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
            WordGuessLevel(1, 1, LevelStatus.UNLOCKED, targetWord = "КНИГА"),
            WordGuessLevel(2, 1, LevelStatus.LOCKED, targetWord = "ЛАМПА"),
            WordGuessLevel(3, 2, LevelStatus.LOCKED, targetWord = "ВЕТЕР"),
            WordGuessLevel(4, 2, LevelStatus.LOCKED, targetWord = "ПОЛКА"),
            WordGuessLevel(5, 3, LevelStatus.LOCKED, targetWord = "ШКОЛА"),
            WordGuessLevel(6, 3, LevelStatus.LOCKED, targetWord = "ИГРОК"),
            WordGuessLevel(7, 4, LevelStatus.LOCKED, targetWord = "ЗЕМЛЯ"),

            // WORD GRID (8–14)
            *WordGridLevels.getLevels(startId = 8).toTypedArray(),

            // WORD BUILD (15–20)
            *WordBuildLevels.getLevels(startId = 15).toTypedArray()
        )
    }
}
