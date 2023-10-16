package com.example.randomanimegenerator.core.navigation

import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.randomanimegenerator.core.util.sharedViewModel
import com.example.randomanimegenerator.feature_details.presentation.CharactersScreen
import com.example.randomanimegenerator.feature_details.presentation.DetailsEvent
import com.example.randomanimegenerator.feature_details.presentation.DetailsScreen
import com.example.randomanimegenerator.feature_details.presentation.DetailsViewModel
import com.example.randomanimegenerator.feature_details.presentation.RecommendationsScreen
import com.example.randomanimegenerator.feature_details.presentation.ReviewsScreen
import com.example.randomanimegenerator.feature_details.presentation.SingleReviewScreen
import com.example.randomanimegenerator.feature_details.presentation.StaffScreen

fun NavGraphBuilder.detailsNavGraph(
    navController: NavHostController,
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
        ) { entry ->
            val scrollState = rememberScrollState()
            val snackBarHostState = remember {
                SnackbarHostState()
            }
            val viewModel = entry.sharedViewModel<DetailsViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()
            DetailsScreen(
                state = state,
                snackBarFlow = viewModel.eventFlow,
                navController = navController,
                onEvent = viewModel::onEvent,
                scrollState = scrollState,
                snackBarHostState = snackBarHostState,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDestination = { route ->
                    navController.navigate(route)
                },
                onNavigateToDetailsScreen = { detailsRoute, id ->
                    navController.navigate("${detailsRoute}/${id}/${viewModel.type}")
                }
            )
        }
        composable(
            route = Destinations.Characters.route
        ) { entry ->
            val viewModel = entry.sharedViewModel<DetailsViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            CharactersScreen(
                characterList = state.characters,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Staff.route
        ) { entry ->
            val viewModel = entry.sharedViewModel<DetailsViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            StaffScreen(
                staff = state.staff,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Reviews.route
        ) { entry ->
            val viewModel = entry.sharedViewModel<DetailsViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            ReviewsScreen(
                reviews = state.reviews,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSingleReview = { review ->
                    viewModel.onEvent(DetailsEvent.NavigateToSingleReview(review))
                    navController.navigate(Destinations.Review.route)
                }
            )
        }
        composable(
            route = Destinations.Review.route
        ) { entry ->
            val viewModel = entry.sharedViewModel<DetailsViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            SingleReviewScreen(
                review = state.spectatedReview?.review ?: "",
                score = state.spectatedReview?.score ?: 0,
                author = state.spectatedReview?.userName ?: "",
                onNavigateBack = {
                    navController.popBackStack()
                    viewModel.onEvent(DetailsEvent.NavigateBackFromSingleReview)
                }
            )
        }
        composable(
            route = Destinations.Recommendations.route
        ) { entry ->
            val viewModel = entry.sharedViewModel<DetailsViewModel>(navController)
            val state by viewModel.state.collectAsStateWithLifecycle()

            RecommendationsScreen(
                recommendations = state.recommendation,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToRecommendation = {
                    navController.navigate("details/$it/${viewModel.type}")
                }
            )
        }
    }
}