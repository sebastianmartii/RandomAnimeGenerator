package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GeneratorScreen(
    viewModel: GeneratorViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle(initialValue = GeneratorState())
    GeneratorScreen(
        generatorState = state,
        onGenerate = {
          viewModel.generate(it)
        },
        onTypeSelect = {
            viewModel.selectType(it)
        },
        onScoreSelect = {
            viewModel.selectScore(it)
        },
        onAmountSelect = {
            viewModel.selectAmount(it)
        }
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun GeneratorScreen(
    generatorState: GeneratorState,
    modifier: Modifier = Modifier,
    onGenerate: (GeneratorState) -> Unit,
    onTypeSelect: (String) -> Unit,
    onScoreSelect: (String) -> Unit,
    onAmountSelect: (String) -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onGenerate(generatorState) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
            ) {
                Text(text = "GENERATE")
            }
        }
    ) { paddingValues ->
        AnimatedContent(targetState = generatorState.hasGenerated) {
            when(generatorState.hasGenerated) {
                false -> {
                    GeneratorSettingsContent(
                        paddingValues = paddingValues,
                        typeSelected = generatorState.typeSelected,
                        onTypeSelect = {
                            onTypeSelect(it)
                        },
                        scoreSelected = generatorState.scoreSelected,
                        onScoreSelect = {
                            onScoreSelect(it)
                        },
                        amountSelected = generatorState.amountSelected,
                        onAmountSelect = {
                            onAmountSelect(it)
                        }
                    )
                }
                true -> {
                    GeneratedContent(
                        paddingValues = paddingValues,
                        singleItem = generatorState.item,
                        listOfItems = generatorState.listOfItems,
                        isLoading = generatorState.isLoading
                    )
                }
            }
        }
    }
}