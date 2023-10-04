package com.example.randomanimegenerator.core.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.randomanimegenerator.core.constants.navigationItems
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorScreen
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorViewModel
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.presentation.AnimeLibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.LibraryEvent
import com.example.randomanimegenerator.feature_library.presentation.LibraryViewModel
import com.example.randomanimegenerator.feature_library.presentation.MangaLibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.animeStatusList
import com.example.randomanimegenerator.feature_library.presentation.librarySortType
import com.example.randomanimegenerator.feature_library.presentation.mangaStatusList

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = Destinations.Generator.route,
        route = Destinations.BottomBarGraph.route
    ) {
        composable(route = Destinations.Generator.route) {
            val viewModel = hiltViewModel<GeneratorViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            GeneratorScreen(
                state = state,
                bottomNavigationItems = navigationItems,
                onEvent = viewModel::onEvent,
                onNavigateToDetailsScreen = {
                    navController.navigate(route = "details/$it/${state.typeSelected.toTypeString()}")
                },
                onNavigateToBottomNavItem = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = Destinations.AnimeLibrary.route,
        ) {
            val focusRequester = remember {
                FocusRequester()
            }
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(sheetState)

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(LibraryEvent.SetType(Type.ANIME))
            }

            AnimeLibraryScreen(
                state = state,
                bottomNavItems = navigationItems,
                eventFlow = viewModel.eventFlow,
                scaffoldState = bottomSheetScaffoldState,
                statusList = animeStatusList,
                sortList = librarySortType,
                onEvent = viewModel::onEvent,
                focusRequester = focusRequester,
                onNavigateToDetailsScreen = { id, type ->
                    navController.navigate("details/$id/$type")
                },
                onNavigateToBottomNavItem = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = Destinations.MangaLibrary.route,
        ) {
            val focusRequester = remember {
                FocusRequester()
            }
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(sheetState)

            LaunchedEffect(key1 = Unit) {
                viewModel.onEvent(LibraryEvent.SetType(Type.MANGA))
            }

            MangaLibraryScreen(
                state = state,
                bottomNavItems = navigationItems,
                eventFlow = viewModel.eventFlow,
                scaffoldState = bottomSheetScaffoldState,
                statusList = mangaStatusList,
                sortList = librarySortType,
                onEvent = viewModel::onEvent,
                focusRequester = focusRequester,
                onNavigateToDetailsScreen = { id, type ->
                    navController.navigate("details/$id/$type")
                },
                onNavigateToBottomNavItem = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}