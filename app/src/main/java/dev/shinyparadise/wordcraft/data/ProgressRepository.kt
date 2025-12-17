package dev.shinyparadise.wordcraft.data

import dev.shinyparadise.wordcraft.model.level.LevelStatus

class ProgressRepository(
    private val dataStore: GameDataStore
) {

    suspend fun saveLevelCompleted(levelId: Int) {
        dataStore.saveLevelStatus(levelId, LevelStatus.COMPLETED.name)
    }

    suspend fun saveLevelUnlocked(levelId: Int) {
        dataStore.saveLevelStatus(levelId, LevelStatus.UNLOCKED.name)
    }

    suspend fun saveHintsUsed(levelId: Int, count: Int) {
        dataStore.saveHintsUsed(levelId, count)
    }
}
