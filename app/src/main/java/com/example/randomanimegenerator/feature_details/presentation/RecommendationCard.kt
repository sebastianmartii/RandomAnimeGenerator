package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation

@Composable
fun RecommendationCard(
    recommendation: Recommendation,
    modifier: Modifier = Modifier,
    onNavigateToRecommendation: (Int) -> Unit
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .width(110.dp)
            .padding(4.dp)
            .clickable {
                onNavigateToRecommendation(recommendation.malId)
            },
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(recommendation.imageUrl)
                    .build(),
                contentDescription = recommendation.title,
                placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                error = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                fallback = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxHeight()
                    .align(Alignment.TopCenter)
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                Color.Black
                            )
                        )
                    )
            ) {
                Text(
                    text = recommendation.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}