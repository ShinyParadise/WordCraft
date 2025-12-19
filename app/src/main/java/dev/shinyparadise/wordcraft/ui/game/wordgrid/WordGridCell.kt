package dev.shinyparadise.wordcraft.ui.game.wordgrid

import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun WordGridCell(
    letter: Char,
    isSelected: Boolean,
    isFound: Boolean,
    modifier: Modifier = Modifier
) {
    val background = when {
        isFound -> MaterialTheme.colorScheme.primary
        isSelected -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surface
    }

    val textColor = when {
        isFound -> MaterialTheme.colorScheme.onPrimary
        isSelected -> MaterialTheme.colorScheme.onSecondaryContainer
        else -> MaterialTheme.colorScheme.onSurface
    }

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(MaterialTheme.shapes.small)
            .background(background)
            .border(1.dp, MaterialTheme.colorScheme.outline, MaterialTheme.shapes.small),
        contentAlignment = Alignment.Center
    ) {
        Text(letter.toString(), color = textColor)
    }
}
