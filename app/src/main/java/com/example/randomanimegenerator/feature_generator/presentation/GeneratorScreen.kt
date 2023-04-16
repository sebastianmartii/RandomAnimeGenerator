package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
            viewModel.selectType(it.toType())
        },
        onScoreSelect = {
            viewModel.selectScore(it)
        },
        onAmountSelect = {
            viewModel.selectAmount(it.toAmount())
        },
        onGeneratingParamsEdit = viewModel::onGeneratingParamsEdit
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
    onAmountSelect: (String) -> Unit,
    onGeneratingParamsEdit: () -> Unit
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End
            ) {
                FloatingActionButton(
                    onClick = onGeneratingParamsEdit,
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit generating params"
                    )
                }
                ExtendedFloatingActionButton(
                    onClick = { onGenerate(generatorState) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                ) {
                    Text(text = "GENERATE")
                }
            }
        }
    ) { paddingValues ->
        AnimatedContent(targetState = generatorState.hasGenerated) {
            when(generatorState.hasGenerated) {
                false -> {
                    GeneratorSettingsContent(
                        paddingValues = paddingValues,
                        typeSelected = generatorState.typeSelected.toTypeString(),
                        onTypeSelect = {
                            onTypeSelect(it)
                        },
                        scoreSelected = generatorState.scoreSelected,
                        onScoreSelect = {
                            onScoreSelect(it)
                        },
                        amountSelected = generatorState.amountSelected.toAmountString(),
                        onAmountSelect = {
                            onAmountSelect(it)
                        }
                    )
                }
                true -> {
                    GeneratedContent(
                        paddingValues = paddingValues,
                        generatedType = generatorState.typeSelected.toTypeString(),
                        listOfItems = generatorState.listOfItems,
                        isLoading = generatorState.isLoading
                    )
                }
            }
        }
    }
}