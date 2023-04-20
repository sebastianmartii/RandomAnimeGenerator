package com.example.randomanimegenerator.core.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorScreen
import com.example.randomanimegenerator.feature_library.presentation.AnimeLibraryScreen
import com.example.randomanimegenerator.feature_library.presentation.MangaLibraryScreen

@SuppressLint("ComposableDestinationInComposeScope")
@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = Destinations.Generator.route) {
        composable(route = Destinations.Generator.route) {
            GeneratorScreen(paddingValues)
        }
        composable(route = Destinations.AnimeLibrary.route) {
            AnimeLibraryScreen(paddingValues)
        }
        composable(route = Destinations.MangaLibrary.route) {
            MangaLibraryScreen(paddingValues)
        }

    }
}