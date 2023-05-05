package com.example.randomanimegenerator.core.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun Navigation(
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = Destinations.BottomBarGraph.route) {
        bottomNavGraph(
            navController = navController,
            paddingValues = paddingValues
        )
        detailsNavGraph(
            navController = navController,
            paddingValues = paddingValues
        )
    }
}

