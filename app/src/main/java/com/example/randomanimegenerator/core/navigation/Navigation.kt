package com.example.randomanimegenerator.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues,
    authenticationClient: AuthenticationClient
) {
    NavHost(navController = navController, startDestination = Destinations.BottomBarGraph.route) {
        bottomNavGraph(
            navController = navController,
            paddingValues = paddingValues,
        )
        detailsNavGraph(
            navController = navController,
            paddingValues = paddingValues
        )
        profileNavGraph(
            navController = navController,
            paddingValues = paddingValues,
            authenticationClient = authenticationClient
        )
    }
}

