package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

@Composable
fun GeneratedContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    state: GeneratorState,
    onEvent: (GeneratorEvent) -> Unit
) {

    when {
        state.listOfItems.isEmpty() && !state.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Generate Random Anime",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(0.6f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Or Change Generating Settings By Pressing The Edit Button",
                    style = MaterialTheme.typography.labelSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(0.4f)
                )
            }
        }

        state.listOfItems.isEmpty() && state.isLoading -> {
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues),
            ) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }

        else -> {
            LazyColumn(
                modifier = modifier
                    .padding(paddingValues),
            ) {
                items(state.listOfItems) { generatedItem ->
                    GeneratedItem(
                        item = generatedItem,
                        add = {
                            onEvent(
                                GeneratorEvent.Add(
                                    type = state.typeSelected,
                                    content = it
                                )
                            )
                        }
                    )

                }
            }
        }
    }
}

@Composable
private fun GeneratedItem(
    item: GeneratorModel,
    modifier: Modifier = Modifier,
    add: (GeneratorModel) -> Unit
) {
    Card(
        modifier = modifier
            .height(intrinsicSize = IntrinsicSize.Max)
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.titleEng,
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.small)
                    .height(170.dp)
                    .aspectRatio(2f / 3f)
            )
            Text(
                text = item.titleEng,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
            )
            IconButton(
                onClick = {
                    add(item)
                },
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add"
                )
            }
        }
    }
}