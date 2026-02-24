package com.kaushalya.abbhyas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KidsStudyApp()
        }
    }
}

@Composable
fun KidsStudyApp() {
    val navController = rememberNavController()
    val viewModel: TestViewModel = viewModel()  // Shared single instance here!

    NavHost(
        navController = navController,
        startDestination = "start"
    ) {
        composable("start") {
            StartTestScreen(navController = navController, viewModel = viewModel)
        }
        composable("test_screen") {
            TestScreen(viewModel = viewModel, navController = navController)
        }
        composable(
            "score/{score}",
            arguments = listOf(navArgument("score") { type = NavType.IntType })
        ) { backStackEntry ->
            val score = backStackEntry.arguments?.getInt("score") ?: 0
            ScoreScreen(score = score, navController = navController)
        }
        composable("history") {
            HistoryScreen(viewModel = viewModel)
        }
    }
}