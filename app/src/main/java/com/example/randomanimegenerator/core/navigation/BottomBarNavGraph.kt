package com.example.randomanimegenerator.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorScreen
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorState
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorViewModel
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.presentation.LibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.LibraryState
import com.example.randomanimegenerator.feature_library.presentation.LibraryViewModel
import com.example.randomanimegenerator.feature_library.presentation.animeStatusList
import com.example.randomanimegenerator.feature_library.presentation.librarySortType
import com.example.randomanimegenerator.feature_library.presentation.mangaStatusList

fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = Destinations.Generator.route,
        route = Destinations.BottomBarGraph.route
    ) {
        composable(route = Destinations.Generator.route) {
            val viewModel = hiltViewModel<GeneratorViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = GeneratorState())
            GeneratorScreen(
                paddingValues,
                state = state,
                onEvent = viewModel::onEvent,
                onDetailsNavigate = {
                    navController.navigate(route = "details/$it/${state.typeSelected.toTypeString()}")
                }
            )
        }
        composable(
            route = Destinations.AnimeLibrary.route,
            arguments = listOf(
                navArgument(name = "type") { type = NavType.StringType}
            )
        ) {
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LibraryState())
            LibraryScreen(
                paddingValues = paddingValues,
                state = state,
                statusList = animeStatusList,
                sortList = librarySortType,
                onEvent = viewModel::onEvent,
                onNavigateToDetailsScreen = {
                    navController.navigate("details/$it/${viewModel.type}")
                }
            )
        }
        composable(
            route = Destinations.MangaLibrary.route,
            arguments = listOf(
                navArgument(name = "type") { type = NavType.StringType}
            )
        ) {
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LibraryState())
            LibraryScreen(
                paddingValues = paddingValues,
                state = state,
                statusList = mangaStatusList,
                sortList = librarySortType,
                onEvent = viewModel::onEvent,
                onNavigateToDetailsScreen = {
                    navController.navigate("details/$it/${viewModel.type}")
                }
            )
        }
    }
}