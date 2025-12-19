package dev.shinyparadise.wordcraft.data.level

import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.WordBuildLevel

object WordBuildLevels {

    fun getLevels(startId: Int = 15): List<WordBuildLevel> {
        return listOf(
            WordBuildLevel(
                id = startId + 0,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                letters = listOf('К','О','Т'),
                words = listOf("КОТ", "ТОК")
            ),
            WordBuildLevel(
                id = startId + 1,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                letters = listOf('Д','О','М'),
                words = listOf("ДОМ", "МОД")
            ),
            WordBuildLevel(
                id = startId + 2,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                letters = listOf('К','А','Р','Т','А'),
                words = listOf("КАРТА", "ТАРА", "АРКА")
            ),
            WordBuildLevel(
                id = startId + 3,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                letters = listOf('С','Т','О','Л'),
                words = listOf("СТОЛ", "ЛОТ")
            ),
            WordBuildLevel(
                id = startId + 4,
                difficulty = 3,
                status = LevelStatus.LOCKED,
                letters = listOf('П','А','Р','Т','А'),
                words = listOf("ПАРТА", "ТАРА", "ПАР")
            ),
            WordBuildLevel(
                id = startId + 5,
                difficulty = 4,
                status = LevelStatus.LOCKED,
                letters = listOf('К','О','М','А','Р'),
                words = listOf("КОМАР", "КОРА", "РОМ")
            )
        )
    }
}
