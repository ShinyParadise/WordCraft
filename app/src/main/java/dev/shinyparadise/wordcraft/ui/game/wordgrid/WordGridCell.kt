package dev.shinyparadise.wordcraft.ui.game.wordgrid

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordGridCell(letter: Char) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .border(1.dp, androidx.compose.ui.graphics.Color.DarkGray),
        contentAlignment = Alignment.Center
    ) {
        Text(letter.toString())
    }
}
