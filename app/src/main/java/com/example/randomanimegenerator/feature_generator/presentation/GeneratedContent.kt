package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

@Composable
fun GeneratedContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    state: GeneratorState,
    onDetailsNavigate: (Int) -> Unit
) {

    when {
        state.listOfItems.isEmpty() && !state.isLoading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.random_anime_generator_text),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.alpha(0.6f)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.generator_hint_text),
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
                        modifier = Modifier.clickable {
                            onDetailsNavigate(generatedItem.malId)
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
    modifier: Modifier = Modifier
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
                model = ImageRequest.Builder(LocalContext.current)
                    .data(item.imageUrl)
                    .build(),
                contentDescription = item.titleEng,
                placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                error = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                fallback = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
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
        }
    }
}