package dev.shinyparadise.wordcraft.ui.game.booster

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.shinyparadise.wordcraft.model.booster.ExtraAttemptBooster
import dev.shinyparadise.wordcraft.model.booster.RevealLetterBooster

@Composable
fun BoostersPanel(
    boosters: Map<String, Int>,
    onRevealLetter: () -> Unit,
    onExtraAttempt: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        BoosterButton(
            title = "Буква",
            count = boosters[RevealLetterBooster.id] ?: 0,
            onClick = onRevealLetter
        )

        BoosterButton(
            title = "+1",
            count = boosters[ExtraAttemptBooster.id] ?: 0,
            onClick = onExtraAttempt
        )
    }
}
