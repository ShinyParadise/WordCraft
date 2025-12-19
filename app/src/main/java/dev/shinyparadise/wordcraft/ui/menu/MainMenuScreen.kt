package dev.shinyparadise.wordcraft.ui.menu

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("WordCraft", fontSize = 32.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = onPlayClick) {
            Text("Играть", fontSize = 24.sp)
        }
    }
}
