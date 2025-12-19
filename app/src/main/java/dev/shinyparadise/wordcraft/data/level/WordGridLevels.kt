package dev.shinyparadise.wordcraft.data.level

import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.WordGridLevel

object WordGridLevels {

    fun getLevels(): List<WordGridLevel> {
        return listOf(
            WordGridLevel(
                id = 6,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('C','A','T','S'),
                    listOf('D','O','G','S'),
                    listOf('B','I','R','D'),
                    listOf('F','I','S','H')
                ),
                words = listOf("CAT", "DOG", "BIRD", "FISH")
            ),
            WordGridLevel(
                id = 7,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('T','R','E','E'),
                    listOf('L','E','A','F'),
                    listOf('W','O','O','D'),
                    listOf('R','O','O','T')
                ),
                words = listOf("TREE", "LEAF", "WOOD", "ROOT")
            ),
            WordGridLevel(
                id = 8,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('S','U','N','S'),
                    listOf('M','O','O','N'),
                    listOf('S','T','A','R'),
                    listOf('S','K','Y','S')
                ),
                words = listOf("SUN", "MOON", "STAR", "SKY")
            ),
            WordGridLevel(
                id = 9,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('F','I','R','E'),
                    listOf('W','A','T','E'),
                    listOf('A','I','R','S'),
                    listOf('E','A','R','T')
                ),
                words = listOf("FIRE", "WATER", "AIR", "EARTH")
            ),
            WordGridLevel(
                id = 10,
                difficulty = 3,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('M','I','N','D'),
                    listOf('B','R','A','I'),
                    listOf('T','H','I','N'),
                    listOf('T','H','O','U')
                ),
                words = listOf("MIND", "BRAIN", "THINK", "THOUGHT")
            )
        )
    }
}
