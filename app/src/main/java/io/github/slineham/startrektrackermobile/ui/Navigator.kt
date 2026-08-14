package io.github.slineham.startrektrackermobile.ui

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.slineham.startrektrackermobile.ui.screens.EpisodeDetailsScreen
import io.github.slineham.startrektrackermobile.ui.screens.EpisodesWatchedScreen
import io.github.slineham.startrektrackermobile.ui.screens.MainMenuScreen
import io.github.slineham.startrektrackermobile.ui.screens.SeriesDetailsScreen
import io.github.slineham.startrektrackermobile.viewmodel.TrackerViewModel

@Composable
fun Navigator() {

    val viewModel: TrackerViewModel = hiltViewModel()

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "mainMenu"
    ) {
        composable(route = "mainMenu") {
            MainMenuScreen(
                viewModel = viewModel,
                navController = navController
            )
        }
        composable(
            route = "seriesDetails/{seriesId}",
            arguments = listOf(navArgument("seriesId") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("seriesId") ?: 0

            SeriesDetailsScreen(
                viewModel = viewModel,
                navController = navController,
                seriesId = id
            )
        }
        composable(
            route = "seriesDetails/{seriesId}/watched",
            arguments = listOf(navArgument("seriesId") { type = NavType.IntType })
        ) { backStackEntry ->

        val id = backStackEntry.arguments?.getInt("seriesId") ?: 0

            EpisodesWatchedScreen(
                viewModel = viewModel,
                seriesId = id
            )
    }
        composable(
            route = "seriesDetails/{seriesId}/{seasonNum}",
            arguments =
                listOf(navArgument("seriesId") { type = NavType.IntType },
                    navArgument("seasonNum") { type = NavType.IntType })
        ) { backStackEntry ->

            val id = backStackEntry.arguments?.getInt("seriesId") ?: 0
            val seasonNumber = backStackEntry.arguments?.getInt("seasonNum") ?:1

            EpisodeDetailsScreen(
                viewModel = viewModel,
                seasonNum = seasonNumber,
                seriesId = id
            )
        }
    }
}