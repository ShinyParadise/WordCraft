package dev.shinyparadise.wordcraft.data

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "wordcraft_prefs")

class GameDataStore(
    private val context: Context
) {

    private object Keys {
        fun levelStatus(levelId: Int) =
            stringPreferencesKey("level_${levelId}_status")

        fun hintsUsed(levelId: Int) =
            intPreferencesKey("level_${levelId}_hints")
    }

    fun levelStatusFlow(levelId: Int): Flow<String?> {
        return context.dataStore.data.map { prefs ->
            prefs[Keys.levelStatus(levelId)]
        }
    }

    suspend fun saveLevelStatus(levelId: Int, status: String) {
        context.dataStore.edit { prefs ->
            prefs[Keys.levelStatus(levelId)] = status
        }
    }

    suspend fun saveHintsUsed(levelId: Int, count: Int) {
        context.dataStore.edit { prefs ->
            prefs[Keys.hintsUsed(levelId)] = count
        }
    }
}
