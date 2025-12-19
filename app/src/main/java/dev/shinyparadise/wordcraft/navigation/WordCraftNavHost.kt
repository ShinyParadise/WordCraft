package dev.shinyparadise.wordcraft.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import dev.shinyparadise.wordcraft.ui.game.GameScreen
import dev.shinyparadise.wordcraft.ui.map.MapScreen
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
            MapScreen(
                onLevelClick = { levelId ->
                    navController.navigate("game/$levelId")
                }
            )
        }

        composable("game/{levelId}") { backStack ->
            val levelId = backStack.arguments?.getString("levelId")!!.toInt()

            GameScreen(
                levelId = levelId,
                onFinish = { result ->
                    navController.navigate(
                        "result/${result.levelId}/${result.result.name}"
                    )
                }
            )
        }

        composable("result/{levelId}/{result}") { backStack ->
            val levelId = backStack.arguments?.getString("levelId")!!.toInt()
            val result = backStack.arguments?.getString("result")!!

            ResultScreen(
                levelId = levelId,
                isWin = result == "WIN",
                onBackToMap = {
                    navController.popBackStack("map", false)
                }
            )
        }

    }
}
