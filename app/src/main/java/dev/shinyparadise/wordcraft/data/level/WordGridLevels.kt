package dev.shinyparadise.wordcraft.data.level

import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.level.WordGridLevel

object WordGridLevels {

    fun getLevels(startId: Int = 8): List<WordGridLevel> {
        return listOf(
            WordGridLevel(
                id = startId + 0,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('К','О','Т','А','И'),
                    listOf('П','У','Л','Ь','Г'),
                    listOf('Л','Е','С','Н','Р'),
                    listOf('Д','О','М','Ы','А'),
                    listOf('Н','О','С','К','Т')
                ),
                words = listOf("КОТ", "ЛЕС", "ДОМ", "НОС", "ИГРА")
            ),
            WordGridLevel(
                id = startId + 1,
                difficulty = 1,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('Р','Е','К','А','М'),
                    listOf('Т','У','Ч','А','О'),
                    listOf('Н','О','Р','Т','Р'),
                    listOf('С','Н','О','П','Е'),
                    listOf('Г','Р','О','М','Ы')
                ),
                words = listOf("РЕКА", "ТУЧА", "ТРОН", "МОРЕ", "ГРОМ")
            ),
            WordGridLevel(
                id = startId + 2,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('З','Е','М','Л','Я'),
                    listOf('С','Г','О','П','Т'),
                    listOf('Н','О','Р','Д','А'),
                    listOf('Л','Р','Е','С','Ы'),
                    listOf('Т','А','Й','Г','А')
                ),
                words = listOf("ЗЕМЛЯ", "ТАЙГА", "МОРЕ", "ГОРА", "ОРДА")
            ),
            WordGridLevel(
                id = startId + 3,
                difficulty = 2,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('К','О','М','А','Р'),
                    listOf('П','А','У','К','Ы'),
                    listOf('Л','И','Х','О','Б'),
                    listOf('С','Е','А','Н','А'),
                    listOf('П','Ч','Е','Л','А')
                ),
                words = listOf("КОМАР", "ПЧЕЛА", "МУХА", "РЫБА", "ПАУК")
            ),
            WordGridLevel(
                id = startId + 4,
                difficulty = 3,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('С','А','Б','В','Т'),
                    listOf('Л','О','Т','О','Е'),
                    listOf('О','З','В','У','К'),
                    listOf('В','И','Г','Р','С'),
                    listOf('О','В','А','Л','Т')
                ),
                words = listOf("СЛОВО", "ТЕКСТ", "ЗВУК", "ЛОТО", "ОВАЛ")
            ),
            WordGridLevel(
                id = startId + 5,
                difficulty = 3,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('К','И','Н','О','Ш'),
                    listOf('Н','У','Р','О','К'),
                    listOf('И','Г','Р','А','О'),
                    listOf('Г','Е','М','А','Л'),
                    listOf('А','Т','А','К','А')
                ),
                words = listOf("КНИГА", "ШКОЛА", "УРОК", "ИГРА", "КИНО")
            ),
            WordGridLevel(
                id = startId + 6,
                difficulty = 4,
                status = LevelStatus.LOCKED,
                grid = listOf(
                    listOf('Т','Е','С','Т','К'),
                    listOf('Х','Б','А','Г','О'),
                    listOf('П','А','Т','Ч','Д'),
                    listOf('Т','Г','Е','К','Е'),
                    listOf('Л','И','Н','К','Р')
                ),
                words = listOf("КОДЕР", "ТЕСТ", "ПАТЧ", "ЛИНК", "БАГ")
            )
        )
    }
}
