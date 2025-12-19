package dev.shinyparadise.wordcraft.data

import dev.shinyparadise.wordcraft.model.level.LevelStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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

    fun observeLevelStatus(levelId: Int): Flow<LevelStatus> {
        return dataStore.levelStatusFlow(levelId)
            .map { stored ->
                when {
                    stored != null -> LevelStatus.valueOf(stored)
                    levelId == 1 -> LevelStatus.UNLOCKED
                    else -> LevelStatus.LOCKED
                }
            }
    }
}
