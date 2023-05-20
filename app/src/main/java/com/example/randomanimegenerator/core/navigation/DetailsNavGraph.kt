package com.example.randomanimegenerator.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.randomanimegenerator.feature_details.presentation.CharactersScreen
import com.example.randomanimegenerator.feature_details.presentation.CharactersViewModel
import com.example.randomanimegenerator.feature_details.presentation.DetailsScreen
import com.example.randomanimegenerator.feature_details.presentation.DetailsState
import com.example.randomanimegenerator.feature_details.presentation.DetailsViewModel
import com.example.randomanimegenerator.feature_details.presentation.RecommendationsScreen
import com.example.randomanimegenerator.feature_details.presentation.RecommendationsViewModel
import com.example.randomanimegenerator.feature_details.presentation.ReviewsScreen
import com.example.randomanimegenerator.feature_details.presentation.ReviewsViewModel
import com.example.randomanimegenerator.feature_details.presentation.SingleReviewScreen
import com.example.randomanimegenerator.feature_details.presentation.StaffScreen
import com.example.randomanimegenerator.feature_details.presentation.StaffViewModel

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
            val scrollState = rememberScrollState()
            val snackBarHostState = remember {
                SnackbarHostState()
            }
            val viewModel = hiltViewModel<DetailsViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(DetailsState())
            DetailsScreen(
                paddingValues = paddingValues,
                state = state,
                snackBarFlow = viewModel.eventFlow,
                navController = navController,
                onEvent = viewModel::onEvent,
                scrollState = scrollState,
                snackBarHostState = snackBarHostState,
            )
        }
        composable(
            route = Destinations.Characters.route,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType },
                navArgument(name = "type") { type = NavType.StringType }
            )
        ) {
            val viewModel = hiltViewModel<CharactersViewModel>()
            val characters by viewModel.characters.collectAsStateWithLifecycle(emptyList())
            CharactersScreen(
                paddingValues = paddingValues,
                characterList = characters,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Staff.route,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType },
                navArgument(name = "type") { type = NavType.StringType },
            )
        ) {
            val viewModel = hiltViewModel<StaffViewModel>()
            val staff by viewModel.staff.collectAsStateWithLifecycle(emptyList())
            StaffScreen(
                paddingValues = paddingValues,
                staff = staff,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Reviews.route,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType },
                navArgument(name = "type") { type = NavType.StringType },
            )
        ) {
            val viewModel = hiltViewModel<ReviewsViewModel>()
            val reviews by viewModel.reviews.collectAsStateWithLifecycle(emptyList())
            ReviewsScreen(
                paddingValues = paddingValues,
                reviews = reviews,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSingleReview = {
                    navController.navigate("review/${it.author}/${it.score}/${it.review}")
                }
            )
        }
        composable(
            route = Destinations.Review.route,
            arguments = listOf(
                navArgument(name = "author") { type = NavType.StringType },
                navArgument(name = "score") { type = NavType.IntType },
                navArgument(name = "review") { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            SingleReviewScreen(
                paddingValues = paddingValues,
                review = navBackStackEntry.arguments?.getString("review") ?: "",
                score = navBackStackEntry.arguments?.getInt("score") ?: 0,
                author = navBackStackEntry.arguments?.getString("author") ?: "",
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Recommendations.route,
            arguments = listOf(
                navArgument(name = "id") { type = NavType.IntType },
                navArgument(name = "type") { type = NavType.StringType },
            )
        ) {
            val viewModel = hiltViewModel<RecommendationsViewModel>()
            val recommendations by viewModel.recommendations.collectAsStateWithLifecycle(emptyList())
            RecommendationsScreen(
                paddingValues = paddingValues,
                recommendations = recommendations,
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