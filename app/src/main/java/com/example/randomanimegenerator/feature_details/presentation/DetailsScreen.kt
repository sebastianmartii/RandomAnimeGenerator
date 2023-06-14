package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.core.constants.animeDetailsStatus
import com.example.randomanimegenerator.core.constants.mangaDetailStatus
import com.example.randomanimegenerator.core.util.ShimmerContent
import com.example.randomanimegenerator.core.util.shimmerEffect
import com.example.randomanimegenerator.feature_details.domain.model.AdditionalInfo
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.SingleReview
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    paddingValues: PaddingValues,
    state: DetailsState,
    snackBarFlow: Flow<DetailsViewModel.UiEvent>,
    navController: NavController,
    scrollState: ScrollState,
    snackBarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onEvent: (DetailsEvent) -> Unit,
) {
    LaunchedEffect(key1 = true) {
        snackBarFlow.collectLatest { event ->
            when (event) {
                is DetailsViewModel.UiEvent.NavigateBack -> {
                    navController.popBackStack()
                }

                is DetailsViewModel.UiEvent.NavigateToDestination -> {
                    navController.navigate(route = event.destinationRoute)
                }

                is DetailsViewModel.UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }
    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onEvent(DetailsEvent.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_action_button_text)
                        )
                    }
                },
                title = {
                    Text(
                        text = state.title,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(DetailsEvent.AddOrRemoveFromFavorites(state.isFavorite)) }) {
                        Icon(
                            imageVector = if (state.isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(id = R.string.add_to_library_text)
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
                .verticalScroll(scrollState),
        ) {
            if (state.isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            MainInfoSection(
                imageUrl = state.imageUrl,
                title = state.title,
                description = state.description,
                isFavorite = state.isFavorite,
                result = state.mainInfoResult,
                synopsisExpanded = state.synopsisExpanded,
                authors = if (state.type == Type.ANIME) state.studios else state.authors,
                statusList = if (state.type == Type.ANIME) animeDetailsStatus else mangaDetailStatus,
                currentStatus = state.libraryStatus,
                onStatusSelect = {
                    onEvent(DetailsEvent.SelectStatus(it))
                },
                onPopUpImage = {
                    onEvent(DetailsEvent.PopUpImage)
                },
                onSynopsisExpand = {
                    onEvent(DetailsEvent.ExpandSynopsis)
                }
            )
            CharactersSection(
                characters = state.characters,
                result = state.charactersResult,
                onNavigateToCharactersScreen = { onEvent(DetailsEvent.NavigateToDestination("characters")) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatsAndReviewsSection(
                additionalInfo = state.additionalInfo,
                reviews = state.reviews,
                result = state.reviewsResult,
                onNavigateToReviewScreen = { onEvent(DetailsEvent.NavigateToDestination("reviews")) },
                onNavigateToSingleReview = { review ->
                    onEvent(
                        DetailsEvent.NavigateToSingleReview(
                            destination = "review",
                            author = review.author,
                            score = review.score,
                            review = review.review
                        )
                    )
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state.type == Type.ANIME) {
                StaffSection(
                    staff = state.staff,
                    result = state.staffResult,
                    onNavigateToStaffScreen = { onEvent(DetailsEvent.NavigateToDestination("staff")) }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            RecommendationsSection(
                recommendations = state.recommendation,
                result = state.recommendationsResult,
                onNavigateToRecommendationsScreen = {
                    onEvent(DetailsEvent.NavigateToDestination("recommendations"))
                },
                onNavigateToRecommendation = {
                    onEvent(DetailsEvent.NavigateToRecommendation("details", it))
                }
            )
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
    if (scrollState.isScrollInProgress && state.getRecommendationsAndStaff) {
        onEvent(DetailsEvent.GenerateRecommendationsAndStaff(state.type))
    }
    if (state.showPopUp) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = {
                onEvent(DetailsEvent.PopUpImage)
            }
        ) {
            AsyncImage(
                model = state.largeImageUrl.ifBlank { state.imageUrl },
                contentDescription = stringResource(id = R.string.pop_up_image_text),
                modifier = Modifier.fillMaxHeight(0.6f)
            )
        }
    }
}


@Composable
private fun MainInfoSection(
    imageUrl: String,
    title: String,
    description: String,
    authors: String,
    statusList: List<LibraryStatus>,
    currentStatus: LibraryStatus,
    isFavorite: Boolean,
    result: Result,
    synopsisExpanded: Boolean,
    onStatusSelect: (LibraryStatus) -> Unit,
    onPopUpImage: () -> Unit,
    onSynopsisExpand: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .animateContentSize()
            .then(if (synopsisExpanded) Modifier.wrapContentHeight() else Modifier.height(250.dp)),
        shape = MaterialTheme.shapes.small.copy(
            topStart = CornerSize(0.dp),
            topEnd = CornerSize(0.dp)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = stringResource(id = R.string.cover_image_text),
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .height(180.dp)
                        .aspectRatio(2f / 3f)
                        .padding(end = 8.dp)
                        .clickable {
                            onPopUpImage()
                        }
                        .then(if (result == Result.LOADING) Modifier.shimmerEffect() else Modifier)
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic),
                        modifier = Modifier.then(if (result == Result.LOADING) Modifier.shimmerEffect() else Modifier)

                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = authors,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.then(if (result == Result.LOADING) Modifier.shimmerEffect() else Modifier)
                    )
                }
            }
            when {
                !synopsisExpanded && description.length > 200 -> {
                    Box {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Light,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .padding(8.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = stringResource(id = R.string.expand_more_text),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .background(
                                    brush = Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
                                        )
                                    )
                                )
                                .clickable {
                                    onSynopsisExpand()
                                }
                        )
                    }
                }

                !synopsisExpanded && description.length < 200 -> {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }

                synopsisExpanded && description.length > 200 -> {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Light,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.ExpandLess,
                        contentDescription = stringResource(id = R.string.expand_less_text),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onSynopsisExpand()
                            }
                    )
                }
            }
        }
    }
    AnimatedVisibility(visible = isFavorite) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Start
        ) {
            statusList.onEach { status ->
                CustomFilterChip(
                    text = status,
                    selected = status.name == currentStatus.name,
                    onSelect = {
                        onStatusSelect(it)
                    }
                )
            }
        }
    }
}

@Composable
private fun CharactersSection(
    characters: List<Character>,
    result: Result,
    modifier: Modifier = Modifier,
    onNavigateToCharactersScreen: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.characters_text),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .alpha(0.8f)
                .padding(4.dp)
        )
        ShimmerContent(
            result = result,
            contentAfterLoading = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    characters.onEach { character ->
                        if (characters.indexOf(character) < 7) {
                            CharacterCard(
                                name = character.name,
                                imageUrl = character.imageUrl,
                            )
                        }
                    }
                    if (characters.size > 7 || characters.isEmpty()) {
                        EmptyCharactersCard(
                            characters = characters,
                            modifier = Modifier.then(
                                if (characters.isEmpty()) Modifier else Modifier.clickable {
                                    onNavigateToCharactersScreen()
                                }
                            )
                        )
                    }
                }
            },
            loadingContent = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).onEach {
                        LoadingRecommendationAndCharacterCard(
                            modifier = Modifier
                                .height(120.dp)
                                .width(90.dp)
                        )
                    }
                }
            },
            errorContent = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EmptyCharactersCard(characters = characters)
                }
            }
        )
    }
}

@Composable
private fun StatsAndReviewsSection(
    additionalInfo: List<AdditionalInfo>,
    reviews: List<Review>,
    result: Result,
    modifier: Modifier = Modifier,
    onNavigateToReviewScreen: () -> Unit,
    onNavigateToSingleReview: (SingleReview) -> Unit
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
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.more_stats_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .alpha(0.8f)
        )
        Text(
            text = stringResource(id = R.string.reviews_text),
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .alpha(0.8f)
                .clickable {
                    onNavigateToReviewScreen()
                }
        )
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
                modifier = Modifier
                    .padding(start = 2.dp, top = 4.dp),
            ) {
                additionalInfo.onEach { info ->
                    if (info.status.length > 1) {
                        CustomStatusBox(
                            statusName = info.statusName,
                            status = info.status
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        ShimmerContent(
            result = result,
            contentAfterLoading = {
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .height(reviewsColumnHeight)
                ) {
                    reviews.onEach { review ->
                        if (reviews.indexOf(review) < 3) {
                            ReviewCard(
                                userName = review.userName,
                                score = review.score,
                                review = review.review,
                                onNavigateToSingleReview = {
                                    onNavigateToSingleReview(it)
                                },
                                modifier = Modifier.then(
                                    if (reviews.indexOf(review) < 2) Modifier.padding(
                                        bottom = 8.dp
                                    ) else Modifier
                                )
                            )
                        }
                    }
                    if (reviews.isEmpty()) {
                        EmptyReviewsCard()
                    }
                }
            },
            loadingContent = {
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .height(reviewsColumnHeight)
                ) {
                    (1..3).onEach {
                        LoadingReviewCard()
                    }
                }
            },
            errorContent = {
                Column(
                    modifier = Modifier
                        .weight(4f)
                        .height(reviewsColumnHeight)
                ) {
                    EmptyReviewsCard()
                }
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StaffSection(
    staff: List<Staff>,
    result: Result,
    modifier: Modifier = Modifier,
    onNavigateToStaffScreen: () -> Unit
) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.staff_text),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .alpha(0.8f)
                .padding(4.dp)
        )
        Surface(
            shape = MaterialTheme.shapes.small,
            color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
            modifier = Modifier.padding(horizontal = 4.dp)
        ) {
            ShimmerContent(
                result = result,
                contentAfterLoading = {
                    FlowColumn(
                        maxItemsInEachColumn = 4,
                        modifier = Modifier.padding(4.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        staff.onEach { staffMember ->
                            if (staff.indexOf(staffMember) < 7) {
                                StaffCard(
                                    name = staffMember.name,
                                    imageUrl = staffMember.imageUrl,
                                    positions = staffMember.position
                                )
                            }
                        }
                        if (staff.size > 7 || staff.isEmpty()) {
                            StaffCard(
                                name = stringResource(id = R.string.see_more_label_text),
                                imageUrl = stringResource(id = R.string.question_mark_staff_image_url),
                                positions = "",
                                modifier = Modifier.clickable {
                                    onNavigateToStaffScreen()
                                }
                            )
                        }
                    }
                },
                loadingContent = {
                    FlowColumn(
                        maxItemsInEachColumn = 4,
                        modifier = Modifier.padding(4.dp),
                    ) {
                        (1..8).onEach {
                            LoadingStaffCard(modifier = Modifier.fillMaxWidth(0.5f))
                        }
                    }
                },
                errorContent = {}
            )
        }
    }
}

@Composable
private fun RecommendationsSection(
    recommendations: List<Recommendation>,
    result: Result,
    modifier: Modifier = Modifier,
    onNavigateToRecommendationsScreen: () -> Unit,
    onNavigateToRecommendation: (Int) -> Unit
) {
    Column(
        modifier = modifier
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = stringResource(id = R.string.recommendations_text),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
            ),
            modifier = Modifier
                .alpha(0.8f)
                .padding(4.dp)
        )
        ShimmerContent(
            result = result,
            contentAfterLoading = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    recommendations.onEach { recommendation ->
                        if (recommendations.indexOf(recommendation) < 7) {
                            RecommendationCard(
                                recommendation = recommendation,
                                onNavigateToRecommendation = {
                                    onNavigateToRecommendation(it)
                                }
                            )
                        }
                    }
                    if (recommendations.size > 7 || recommendations.isEmpty()) {
                        EmptyRecommendationsCard(
                            recommendations = recommendations,
                            modifier = Modifier.then(
                                if (recommendations.isEmpty()) Modifier else Modifier.clickable {
                                    onNavigateToRecommendationsScreen()
                                }
                            )
                        )
                    }
                }
            },
            loadingContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..4).onEach {
                        LoadingRecommendationAndCharacterCard(
                            modifier = Modifier
                                .height(150.dp)
                                .width(110.dp)
                        )
                    }
                }
            },
            errorContent = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    EmptyRecommendationsCard(recommendations = recommendations)
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomFilterChip(
    text: LibraryStatus,
    selected: Boolean,
    onSelect: (LibraryStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = { onSelect(text) },
        label = {
            Text(
                text = text.name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
        },
        modifier = modifier.padding(
            start = 4.dp,
            end = 2.dp
        ),
        shape = MaterialTheme.shapes.medium,
    )
}

@Composable
fun LoadingReviewCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(200.dp)
            .padding(bottom = 8.dp),
        shape = MaterialTheme.shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        )
    }
}

@Composable
fun LoadingRecommendationAndCharacterCard(
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shimmerEffect()
        )
    }
}

@Composable
fun LoadingStaffCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                top = 8.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .clip(MaterialTheme.shapes.small)
                .height(100.dp)
                .aspectRatio(2f / 3f)
                .padding(end = 4.dp)
                .shimmerEffect()
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "",
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "",
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .shimmerEffect()
            )
        }
    }
}