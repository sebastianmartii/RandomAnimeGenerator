package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun StaffCard(
    name: String,
    imageUrl: String,
    positions: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(0.5f)
            .padding(
                start = 8.dp,
                top = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .build(),
            contentDescription = name,
            placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            error = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            fallback = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .height(100.dp)
                .aspectRatio(2f / 3f)
                .padding(end = 4.dp)
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = positions,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Light,
                maxLines = 1,
            )
        }
    }
}