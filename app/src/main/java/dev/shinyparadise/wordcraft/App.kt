package dev.shinyparadise.wordcraft

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import dev.shinyparadise.wordcraft.navigation.WordCraftNavHost

@Composable
fun WordCraftApp() {
    MaterialTheme {
        Surface {
            WordCraftNavHost()
        }
    }
}
