package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun GeneratorScreen(
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier
) {
    val viewModel = hiltViewModel<GeneratorViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle(GeneratorState())
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Random Anime Generator",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    PlainTooltipBox(
                        tooltip = { 
                            Text(text = "Edit")
                        }
                    ) {
                        IconButton(
                            onClick = { viewModel.onEvent(GeneratorEvent.EditGeneratorParams) },
                            modifier = Modifier
                                .tooltipAnchor()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "edit"
                            )
                        }
                    }
                    PlainTooltipBox(
                        tooltip = {
                            Text(text = "Generate")
                        }
                    ){
                        IconButton(
                            onClick = { viewModel.onEvent(GeneratorEvent.Generate(state)) },
                            modifier = Modifier.tooltipAnchor()
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = "generate"
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp),
                )
            )
        },
        modifier = modifier.padding(paddingValues)
    ) {values ->
        AnimatedContent(targetState = state.editGeneratingParams) {
            when (it) {
                false -> {
                    GeneratedContent(
                        paddingValues = values,
                        state = state,
                        viewModel = viewModel
                    )
                }
                true -> {
                    GeneratorSettings(
                        paddingValues = values,
                        state = state,
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}