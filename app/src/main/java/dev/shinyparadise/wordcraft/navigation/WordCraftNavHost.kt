package dev.shinyparadise.wordcraft.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import dev.shinyparadise.wordcraft.ui.game.GameScreen
import dev.shinyparadise.wordcraft.ui.map.LevelMapScreen
import dev.shinyparadise.wordcraft.ui.menu.MainMenuScreen
import dev.shinyparadise.wordcraft.ui.result.ResultScreen

@Composable
fun WordCraftNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "menu"
    ) {
        composable("menu") {
            MainMenuScreen(
                onPlayClick = { navController.navigate("map") }
            )
        }

        composable("map") {
            LevelMapScreen(
                onLevelSelected = { levelId ->
                    navController.navigate("game/$levelId")
                }
            )
        }

        composable("game/{levelId}") { backStack ->
            val levelId = backStack.arguments?.getString("levelId")!!.toInt()
            GameScreen(
                levelId = levelId,
                onFinish = {
                    navController.navigate("result/$levelId")
                }
            )
        }

        composable("result/{levelId}") { backStack ->
            val levelId = backStack.arguments?.getString("levelId")!!.toInt()
            ResultScreen(
                levelId = levelId,
                onBackToMap = {
                    navController.popBackStack("map", false)
                }
            )
        }
    }
}
