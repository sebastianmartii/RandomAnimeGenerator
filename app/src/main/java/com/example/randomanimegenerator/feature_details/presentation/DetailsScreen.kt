package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomanimegenerator.feature_details.domain.model.AdditionalInfo
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_generator.presentation.Type


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    paddingValues: PaddingValues,
    state: DetailsState,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    onNavigateToDescriptionScreen: () -> Unit,
    onEvent: (DetailsEvent) -> Unit
) {
    val scrollState = rememberScrollState()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back Action Button"
                        )
                    }

                },
                title = {
                    Text(
                        text = state.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(DetailsEvent.AddOrRemoveFromFavorites(state.isFavorite)) }) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Add to library"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            )
        },
        modifier = modifier
            .padding(paddingValues)
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            MainInfoSection(
                imageUrl = state.imageUrl,
                title = state.title,
                description = state.description,
                onNavigateToDescriptionScreen = onNavigateToDescriptionScreen
            )
            Spacer(modifier = Modifier.height(8.dp))
            CharactersSection(characters = state.characters)
            Spacer(modifier = Modifier.height(8.dp))
            StatsAndReviewsSection(
                additionalInfo = state.additionalInfo,
                reviews = state.reviews,
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state.type == Type.ANIME && state.staff.isNotEmpty()) {
                StaffSection(staff = state.staff)
                Spacer(modifier = Modifier.height(8.dp))
            }
            RecommendationsSection(recommendations = state.recommendation)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    if (scrollState.isScrollInProgress && state.getRecommendations) {
        onEvent(DetailsEvent.GenerateRecommendations)
    }
    if (scrollState.isScrollInProgress && state.getStaff && state.type == Type.ANIME) {
        onEvent(DetailsEvent.GenerateStaff)
    }
}


@Composable
private fun MainInfoSection(
    imageUrl: String,
    title: String,
    description: String,
    onNavigateToDescriptionScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "main image",
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .height(220.dp)
                    .aspectRatio(2f / 3f)
                    .padding(end = 8.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Light,
                    maxLines = 8,
                    modifier = Modifier.clickable {
                        onNavigateToDescriptionScreen()
                    }
                )
            }
        }
    }
}

@Composable
private fun CharactersSection(
    characters: List<Character>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Characters",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )
        Text(
            text = "See More...",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )
    }
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(120.dp),
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(characters) { character ->
                if(characters.indexOf(character) < 7){
                    CharacterCard(
                        name = character.name,
                        imageUrl = character.imageUrl
                    )
                }
            }
            if (characters.size < 5) {
                item { 
                    EmptyCharactersCard(characters = characters)
                }
            }
        }
    }
}

@Composable
private fun StatsAndReviewsSection(
    additionalInfo: List<AdditionalInfo>,
    reviews: List<Review>,
    modifier: Modifier = Modifier
) {
    var reviewsColumnHeight by remember {
        mutableStateOf(0.dp)
    }
    val localDensity = LocalDensity.current
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "More Stats",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .weight(2f)
                .alpha(0.7f)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Row(
            modifier = Modifier.weight(4f),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Reviews",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.alpha(0.7f)
            )
            Text(
                text = "See More...",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.alpha(0.7f)
            )
        }
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Surface(
            modifier = Modifier
                .weight(2f)
                .onGloballyPositioned { coordinates ->
                    reviewsColumnHeight = with(localDensity) { coordinates.size.height.toDp() }
                },
            shape = MaterialTheme.shapes.extraSmall,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        ) {
            Column(
                modifier = Modifier.padding(start = 2.dp, top = 4.dp),
            ) {
                additionalInfo.onEach { info ->
                    if (info.status.length > 1){
                        CustomStatusBox(
                            statusName = info.statusName,
                            status = info.status
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier
                .weight(4f)
                .height(reviewsColumnHeight)
        ) {
            if (reviews.isEmpty()) {
                EmptyReviewsCard()
            } else {
                reviews.onEach { review ->
                    if (reviews.indexOf(review) < 3) {
                        ReviewCard(
                            userName = review.userName,
                            score = review.score,
                            review = review.review
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StaffSection(
    staff: List<Staff>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Staff",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )
        Text(
            text = "See More...",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )
    }
    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp),
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            maxItemsInEachRow = 2
        ) {
            staff.onEach { staffMember ->
                if (staff.indexOf(staffMember) < 4){
                    StaffCard(
                        name = staffMember.name,
                        imageUrl = staffMember.imageUrl,
                        positions = staffMember.position,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendationsSection(
    recommendations: List<Recommendation>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Recommendations",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )
        Text(
            text = "See More...",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.alpha(0.7f)
        )
    }
    Surface(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .height(120.dp),
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(recommendations) { recommendation ->
                if(recommendations.indexOf(recommendation) < 5){
                    RecommendationCard(
                        title = recommendation.title,
                        imageUrl = recommendation.imageUrl
                    )
                }
            }
            if (recommendations.size < 5) {
                item { 
                    EmptyRecommendationsCard(recommendations = recommendations)
                }
            }
        }
    }
}
