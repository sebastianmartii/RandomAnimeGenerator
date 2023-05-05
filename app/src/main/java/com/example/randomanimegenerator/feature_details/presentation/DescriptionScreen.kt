package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DescriptionScreen(
    title: String,
    imageUrl: String,
    synopsis: String,
    modifier: Modifier = Modifier
) {
    val decodedUrl = URLDecoder.decode(imageUrl, StandardCharsets.UTF_8.toString())
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Surface(
            modifier = Modifier
                .weight(1f),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AsyncImage(
                    model = decodedUrl,
                    contentDescription = title,
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .height(300.dp)
                        .aspectRatio(2f / 3f)
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Surface(
            modifier = Modifier
                .weight(2f),
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            shape = MaterialTheme.shapes.extraSmall
        ) {
            Text(
                text = synopsis,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}