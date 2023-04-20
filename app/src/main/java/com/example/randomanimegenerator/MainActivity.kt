package com.example.randomanimegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.randomanimegenerator.core.constants.navigationItems
import com.example.randomanimegenerator.core.navigation.BottomNavigationBar
import com.example.randomanimegenerator.core.navigation.Navigation
import com.example.randomanimegenerator.ui.theme.RandomAnimeGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
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
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(
                        navController = navController,
                        paddingValues = it
                    )
                }
            }
        }
    }
}