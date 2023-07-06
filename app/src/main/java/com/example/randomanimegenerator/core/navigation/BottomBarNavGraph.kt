package com.example.randomanimegenerator.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.focus.FocusRequester
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorScreen
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorState
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorViewModel
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.presentation.AnimeLibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.LibraryEvent
import com.example.randomanimegenerator.feature_library.presentation.LibraryState
import com.example.randomanimegenerator.feature_library.presentation.LibraryViewModel
import com.example.randomanimegenerator.feature_library.presentation.MangaLibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.animeStatusList
import com.example.randomanimegenerator.feature_library.presentation.librarySortType
import com.example.randomanimegenerator.feature_library.presentation.mangaStatusList

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.bottomNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
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
        ) {
            val focusRequester = remember {
                FocusRequester()
            }
            val viewModel = hiltViewModel<LibraryViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LibraryState())

            val scope = rememberCoroutineScope()
            val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(sheetState)

            LaunchedEffect(key1 = true) {
                viewModel.onEvent(LibraryEvent.SetType(Type.ANIME))
            }

            AnimeLibraryScreen(
                paddingValues = paddingValues,
                state = state,
                scope = scope,
                scaffoldState = bottomSheetScaffoldState,
                statusList = animeStatusList,
                sortList = librarySortType,
                onEvent = viewModel::onEvent,
                focusRequester = focusRequester,
                onNavigateToDetailsScreen = { id, type ->
                    navController.navigate("details/$id/$type")
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
            val state by viewModel.state.collectAsStateWithLifecycle(initialValue = LibraryState())

            val scope = rememberCoroutineScope()
            val sheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Hidden, skipHiddenState = false)
            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(sheetState)

            LaunchedEffect(key1 = true) {
                viewModel.onEvent(LibraryEvent.SetType(Type.MANGA))
            }

            MangaLibraryScreen(
                paddingValues = paddingValues,
                state = state,
                scope = scope,
                scaffoldState = bottomSheetScaffoldState,
                statusList = mangaStatusList,
                sortList = librarySortType,
                onEvent = viewModel::onEvent,
                focusRequester = focusRequester,
                onNavigateToDetailsScreen = { id, type ->
                    navController.navigate("details/$id/$type")
                }
            )
        }
    }
}