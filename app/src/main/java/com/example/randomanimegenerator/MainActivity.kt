package com.example.randomanimegenerator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.randomanimegenerator.feature_generator.presentation.GeneratorScreen
import com.example.randomanimegenerator.ui.theme.RandomAnimeGeneratorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RandomAnimeGeneratorTheme {
                GeneratorScreen()
            }
        }
    }
}
