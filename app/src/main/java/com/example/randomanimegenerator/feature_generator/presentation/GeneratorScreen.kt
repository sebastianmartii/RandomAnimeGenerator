package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratorScreen(
    paddingValues: PaddingValues,
    state: GeneratorState,
    modifier: Modifier = Modifier,
    onEvent: (GeneratorEvent) -> Unit,
    onDetailsNavigate: (Int) -> Unit
) {
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
                            onClick = { onEvent(GeneratorEvent.EditGeneratorParams) },
                            modifier = Modifier
                                .tooltipTrigger()
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
                            onClick = { onEvent(GeneratorEvent.Generate(state)) },
                            modifier = Modifier
                                .tooltipTrigger()
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
                        onDetailsNavigate = onDetailsNavigate
                    )
                }
                true -> {
                    GeneratorSettings(
                        paddingValues = values,
                        state = state,
                        onEvent = onEvent
                    )
                }
            }
        }
    }
}