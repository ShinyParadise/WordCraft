package dev.shinyparadise.wordcraft.ui.game.booster

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BoosterButton(
    title: String,
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = count > 0,
        modifier = modifier
    ) {
        Text(text = "$title ($count)")
    }
}
