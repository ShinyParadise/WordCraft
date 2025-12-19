package dev.shinyparadise.wordcraft.model.booster

sealed interface Booster {
    val id: String
    val title: String
    val description: String
    val maxCount: Int
}

object RevealLetterBooster : Booster {
    override val id = "reveal_letter"
    override val title = "Открыть букву"
    override val description = "Открывает одну правильную букву"
    override val maxCount = 3
}

object ExtraAttemptBooster : Booster {
    override val id = "extra_attempt"
    override val title = "Доп. попытка"
    override val description = "Добавляет одну попытку"
    override val maxCount = 2
}
