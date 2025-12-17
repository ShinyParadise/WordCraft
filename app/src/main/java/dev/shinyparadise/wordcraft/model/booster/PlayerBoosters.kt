package dev.shinyparadise.wordcraft.model.booster

data class PlayerBoosters(
    val boosters: Map<BoosterType, Int>
) {

    fun canUse(type: BoosterType): Boolean =
        boosters[type]?.let { it > 0 } ?: false

    fun use(type: BoosterType): PlayerBoosters {
        val current = boosters[type] ?: return this
        if (current <= 0) return this

        return copy(
            boosters = boosters + (type to current - 1)
        )
    }
}
