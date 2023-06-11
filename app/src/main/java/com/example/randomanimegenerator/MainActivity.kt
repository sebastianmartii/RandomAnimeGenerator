package com.example.randomanimegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.example.randomanimegenerator.core.constants.navigationItems
import com.example.randomanimegenerator.core.navigation.BottomNavigationBar
import com.example.randomanimegenerator.core.navigation.Navigation
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import com.example.randomanimegenerator.ui.theme.RandomAnimeGeneratorTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authClient by lazy {
        AuthenticationClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomAnimeGeneratorTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = navigationItems,
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route) {
                                    popUpTo(navController.graph.findStartDestination().id)
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                ) {
                    Navigation(
                        navController = navController,
                        paddingValues = it,
                        authenticationClient = authClient
                    )
                }
            }
        }
    }
}