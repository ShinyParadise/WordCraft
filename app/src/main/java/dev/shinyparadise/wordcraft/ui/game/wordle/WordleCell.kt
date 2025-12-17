package dev.shinyparadise.wordcraft.ui.game.wordle

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.shinyparadise.wordcraft.model.state.LetterResult

@Composable
fun WordleCell(
    letter: Char,
    result: LetterResult,
) {
    val background = when (result) {
        LetterResult.CORRECT -> Color(0xFF4CAF50)
        LetterResult.PRESENT -> Color(0xFFFFC107)
        LetterResult.ABSENT -> Color(0xFFB0BEC5)
    }

    Box(
        modifier = Modifier
            .size(56.dp)
            .border(1.dp, Color.DarkGray)
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = letter.takeIf { it != ' ' }?.toString() ?: "",
            fontSize = 22.sp
        )
    }
}
