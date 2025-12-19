package dev.shinyparadise.wordcraft

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import dev.shinyparadise.wordcraft.navigation.WordCraftNavHost
import dev.shinyparadise.wordcraft.ui.theme.WordCraftTheme

@Composable
fun WordCraftApp() {
    WordCraftTheme {
        Surface {
            WordCraftNavHost()
        }
    }
}
