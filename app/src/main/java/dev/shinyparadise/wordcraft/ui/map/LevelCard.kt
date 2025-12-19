package dev.shinyparadise.wordcraft.ui.map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.shinyparadise.wordcraft.model.level.LevelStatus
import dev.shinyparadise.wordcraft.model.map.MapLevelItem

@Composable
fun LevelCard(
    level: MapLevelItem,
    onClick: () -> Unit
) {
    val enabled = level.status != LevelStatus.LOCKED

    val color = when (level.status) {
        LevelStatus.COMPLETED -> MaterialTheme.colorScheme.primary
        LevelStatus.UNLOCKED -> MaterialTheme.colorScheme.secondary
        LevelStatus.LOCKED -> MaterialTheme.colorScheme.surfaceVariant
    }

    Card(
        modifier = Modifier
            .aspectRatio(1f)
            .clickable(enabled = enabled, onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = level.id.toString(),
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}
