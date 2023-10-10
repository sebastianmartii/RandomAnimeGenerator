package com.example.randomanimegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.randomanimegenerator.core.navigation.Destinations
import com.example.randomanimegenerator.core.navigation.bottomNavGraph
import com.example.randomanimegenerator.core.navigation.detailsNavGraph
import com.example.randomanimegenerator.core.navigation.profileNavGraph
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClientImpl
import com.example.randomanimegenerator.ui.theme.RandomAnimeGeneratorTheme
import com.google.android.gms.auth.api.identity.Identity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authClient by lazy {
        AuthenticationClientImpl(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomAnimeGeneratorTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Destinations.BottomBarGraph.route
                ) {
                    bottomNavGraph(navController)
                    detailsNavGraph(navController)
                    profileNavGraph(navController, authClient)
                }
            }
        }
    }
}