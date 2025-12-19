package dev.shinyparadise.wordcraft.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dev.shinyparadise.wordcraft.data.AllLevelsProvider
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import dev.shinyparadise.wordcraft.data.GameDataStore
import dev.shinyparadise.wordcraft.data.ProgressRepository
import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.map.MapLevelItem

class MapViewModel(
    app: Application
) : AndroidViewModel(app) {

    private val repository =
        ProgressRepository(GameDataStore(app))

    private val _levels = MutableStateFlow<List<MapLevelItem>>(emptyList())
    val levels: StateFlow<List<MapLevelItem>> = _levels

    init {
        loadLevels()
    }

    private fun loadLevels() {
        viewModelScope.launch {
            val baseLevels = AllLevelsProvider.getAllLevels()

            combine(
                baseLevels.map { level ->
                    repository.observeLevelStatus(level.id)
                        .map { status ->
                            MapLevelItem(
                                id = level.id,
                                type = level.type,
                                status = status
                            )
                        }
                }
            ) { items ->
                items.toList()
            }.collect {
                _levels.value = unlockSequentiallyAndPersist(it)
            }
        }
    }

    private suspend fun unlockSequentiallyAndPersist(
        levels: List<MapLevelItem>
    ): List<MapLevelItem> {

        val sorted = levels.sortedBy { it.id }.toMutableList()

        for (i in 0 until sorted.lastIndex) {
            if (sorted[i].status == LevelStatus.COMPLETED &&
                sorted[i + 1].status == LevelStatus.LOCKED
            ) {
                repository.tryUnlockLevel(sorted[i + 1].id)
                sorted[i + 1] = sorted[i + 1].copy(
                    status = LevelStatus.UNLOCKED
                )
            }
        }
        return sorted
    }
}
