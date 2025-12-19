package dev.shinyparadise.wordcraft.ui.menu

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.shinyparadise.wordcraft.ui.game.GameScaffold

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text("WordCraft")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPlayClick) {
            Text("Играть")
        }
    }
}
