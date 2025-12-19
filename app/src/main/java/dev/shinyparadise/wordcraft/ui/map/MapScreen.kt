package dev.shinyparadise.wordcraft.ui.map

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.shinyparadise.wordcraft.viewmodel.MapViewModel

@Composable
fun MapScreen(
    onLevelClick: (Int) -> Unit
) {
    val viewModel: MapViewModel = viewModel()
    val levels by viewModel.levels.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(levels) { level ->
            LevelCard(
                level = level,
                onClick = { onLevelClick(level.id) }
            )
        }
    }
}
