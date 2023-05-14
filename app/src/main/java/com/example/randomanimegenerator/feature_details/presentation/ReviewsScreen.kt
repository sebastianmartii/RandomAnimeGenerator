package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.SingleReview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    paddingValues: PaddingValues,
    reviews: List<Review>,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToSingleReview: (SingleReview) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Reviews",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Action Button"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            )
        },
        modifier = modifier.padding(paddingValues)
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
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}