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
import com.example.randomanimegenerator.feature_details.presentation.DescriptionScreen
import com.example.randomanimegenerator.feature_details.presentation.DetailsScreen
import com.example.randomanimegenerator.feature_details.presentation.DetailsState
import com.example.randomanimegenerator.feature_details.presentation.DetailsViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    navigation(
        startDestination = Destinations.Details.route,
        route = Destinations.DetailsNavGraph.route
    ) {
        composable(
            route = Destinations.Details.route,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType },
                navArgument(name = "type") { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<DetailsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(DetailsState())
            val encodedUrl = URLEncoder.encode(state.imageUrl, StandardCharsets.UTF_8.toString())
            DetailsScreen(
                paddingValues = paddingValues,
                state = state,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onEvent = viewModel::onEvent,
                onNavigateToDescriptionScreen = {
                    navController.navigate("description/${state.title}/$encodedUrl/${state.description}")
                }
            )
        }
        composable(
            route = Destinations.Description.route,
            arguments = listOf(
                navArgument(name = "title") { type = NavType.StringType },
                navArgument(name = "image") { type = NavType.StringType },
                navArgument(name = "synopsis") { type = NavType.StringType },
            )
        ) {
            DescriptionScreen(
                title = it.arguments?.getString("title") ?: "",
                imageUrl = it.arguments?.getString("imageUrl") ?: "",
                synopsis = it.arguments?.getString("synopsis") ?: "",
            )
        }
    }
}