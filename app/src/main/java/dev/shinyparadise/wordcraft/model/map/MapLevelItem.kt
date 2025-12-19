package dev.shinyparadise.wordcraft.model.map

import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.LevelType

data class MapLevelItem(
    val id: Int,
    val type: LevelType,
    val status: LevelStatus
)
