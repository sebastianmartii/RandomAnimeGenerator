package com.example.randomanimegenerator.core.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.randomanimegenerator.feature_details.presentation.DetailsScreen
import com.example.randomanimegenerator.feature_details.presentation.DetailsState
import com.example.randomanimegenerator.feature_details.presentation.DetailsViewModel
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorScreen
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorState
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorViewModel
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.presentation.LibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.LibraryState
import com.example.randomanimegenerator.feature_library.presentation.LibraryViewModel
import com.example.randomanimegenerator.feature_library.presentation.animeStatusList
import com.example.randomanimegenerator.feature_library.presentation.mangaStatusList

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = Destinations.Generator.route) {
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
        composable(route = Destinations.AnimeLibrary.route) {
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LibraryState())
            LaunchedEffect(key1 = true) {
                viewModel.setType(Type.ANIME)
            }
            LibraryScreen(
                paddingValues = paddingValues,
                state = state,
                statusList = animeStatusList,
                onSelect = viewModel::selectStatus
            )
        }
        composable(route = Destinations.MangaLibrary.route) {
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LibraryState())
            LaunchedEffect(key1 = true) {
                viewModel.setType(Type.MANGA)
            }
            LibraryScreen(
                paddingValues = paddingValues,
                state = state,
                statusList = mangaStatusList,
                onSelect = viewModel::selectStatus
            )
        }
        composable(
            route = Destinations.Details.route,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType},
                navArgument(name = "type") { type = NavType.StringType}
            )
        ) {
            val viewModel = hiltViewModel<DetailsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(DetailsState())
            DetailsScreen(
                paddingValues = paddingValues,
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent
            )
        }
    }
}