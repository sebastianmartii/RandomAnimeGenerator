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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomanimegenerator.R

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
                        text = stringResource(id = R.string.random_anime_generator_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    PlainTooltipBox(
                        tooltip = {
                            Text(text = stringResource(id = R.string.edit_text))
                        }
                    ) {
                        IconButton(
                            onClick = { onEvent(GeneratorEvent.EditGeneratorParams) },
                            modifier = Modifier
                                .tooltipTrigger()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(id = R.string.edit_text)
                            )
                        }
                    }
                    PlainTooltipBox(
                        tooltip = {
                            Text(text = stringResource(id = R.string.generate_text))
                        }
                    ) {
                        IconButton(
                            onClick = { onEvent(GeneratorEvent.Generate(state)) },
                            modifier = Modifier
                                .tooltipTrigger()
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = stringResource(id = R.string.generate_text)
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
    ) { values ->
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