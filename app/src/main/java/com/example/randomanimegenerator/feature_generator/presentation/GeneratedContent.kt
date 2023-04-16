package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

@Composable
fun GeneratedContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    generatedType: String,
    listOfItems: List<GeneratorModel> = emptyList(),
    isLoading: Boolean
) {
    LazyColumn(
        modifier = modifier
            .padding(paddingValues),
    ) {
        item {
            Text(
                text = "Generate Random $generatedType",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
            )
        }
        if (isLoading) {
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
        } else {
            listOfItems.shuffled()
            items(listOfItems) { generatedItem ->
                GeneratedItem(
                    title = generatedItem.title,
                    imageUrl = generatedItem.imageUrl
                )
            }
        }
    }
}

@Composable
private fun GeneratedItem(
    title: String,
    imageUrl: String,
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
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
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
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.small)
                    .height(150.dp)
                    .aspectRatio(2f / 3f)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .weight(2f)
                    .padding(16.dp)
            )
        }
    }
}