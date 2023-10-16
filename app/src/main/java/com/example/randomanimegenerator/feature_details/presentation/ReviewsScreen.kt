package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.randomanimegenerator.feature_details.domain.model.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    reviews: List<Review>,
    onNavigateBack: () -> Unit,
    onNavigateToSingleReview: (Review) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.reviews_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_action_button_text)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            )
        }
    ) { values ->
        LazyColumn(modifier = Modifier.padding(values)) {
            items(reviews) { review ->
                ReviewCard(
                    userName = review.userName,
                    score = review.score,
                    review = review.review,
                    onNavigateToSingleReview = {
                        onNavigateToSingleReview(it)
                    },
                    modifier = Modifier
                        .height(260.dp)
                        .padding(horizontal = 8.dp, vertical = 8.dp)
                )
            }
        }
    }
}