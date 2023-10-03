package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.core.navigation.BottomNavItem
import com.example.randomanimegenerator.core.navigation.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneratorScreen(
    state: GeneratorState,
    bottomNavigationItems: List<BottomNavItem>,
    onEvent: (GeneratorEvent) -> Unit,
    onNavigateToDetailsScreen: (Int) -> Unit,
    onNavigateToBottomNavItem: (String) -> Unit
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
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip {
                                Text(text = stringResource(id = R.string.edit_text))
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(
                            onClick = { onEvent(GeneratorEvent.EditGeneratorParams) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(id = R.string.edit_text)
                            )
                        }
                    }
                    TooltipBox(
                        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
                        tooltip = {
                            PlainTooltip {
                                Text(text = stringResource(id = R.string.generate_text))
                            }
                        },
                        state = rememberTooltipState()
                    ) {
                        IconButton(
                            onClick = { onEvent(GeneratorEvent.Generate(state)) }
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
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItems,
                selectedItemIndex = 0,
                onItemClick = onNavigateToBottomNavItem
            )
        }
    ) { values ->
        AnimatedContent(targetState = state.editGeneratingParams, label = "") {
            when (it) {
                false -> {
                    GeneratedContent(
                        paddingValues = values,
                        state = state,
                        onDetailsNavigate = onNavigateToDetailsScreen
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