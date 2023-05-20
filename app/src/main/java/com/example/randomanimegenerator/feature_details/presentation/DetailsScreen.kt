package com.example.randomanimegenerator.feature_details.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.randomanimegenerator.core.constants.animeDetailsStatus
import com.example.randomanimegenerator.core.constants.mangaDetailStatus
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
            when(event) {
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
                            contentDescription = "Back Action Button"
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
                            contentDescription = "Add to library"
                        )
                    }
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "See more options"
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
                onNavigateToCharactersScreen = { onEvent(DetailsEvent.NavigateToDestination("characters")) }
            )
            Spacer(modifier = Modifier.height(8.dp))
            StatsAndReviewsSection(
                additionalInfo = state.additionalInfo,
                reviews = state.reviews,
                onNavigateToReviewScreen = { onEvent(DetailsEvent.NavigateToDestination("reviews")) },
                onNavigateToSingleReview = { review ->
                    onEvent(DetailsEvent.NavigateToSingleReview(
                        destination = "review",
                        author = review.author,
                        score = review.score,
                        review = review.review
                    ))
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (state.type == Type.ANIME && state.staff.isNotEmpty()) {
                StaffSection(
                    staff = state.staff,
                    onNavigateToStaffScreen = { onEvent(DetailsEvent.NavigateToDestination("staff")) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            RecommendationsSection(
                recommendations = state.recommendation,
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
    if (scrollState.isScrollInProgress && state.getRecommendations) {
        onEvent(DetailsEvent.GenerateRecommendations)
    }
    if (scrollState.isScrollInProgress && state.getStaff && state.type == Type.ANIME) {
        onEvent(DetailsEvent.GenerateStaff)
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
                contentDescription = "PopUp Image",
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
        shape = MaterialTheme.shapes.small.copy(topStart = CornerSize(0.dp), topEnd = CornerSize(0.dp)),
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
                    contentDescription = "main image",
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.small)
                        .height(180.dp)
                        .aspectRatio(2f / 3f)
                        .padding(end = 8.dp)
                        .clickable {
                            onPopUpImage()
                        }
                )
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(fontStyle = FontStyle.Italic)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = authors,
                        style = MaterialTheme.typography.labelMedium
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
                            contentDescription = "expand for more synopsis info",
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
                        imageVector =  Icons.Default.ExpandLess,
                        contentDescription = "expand for more synopsis info",
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
                .padding(horizontal = 4.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.Top
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
    modifier: Modifier = Modifier,
    onNavigateToCharactersScreen: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Characters",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(horizontal = 12.dp)
                .alpha(0.7f)
                .clickable {
                    onNavigateToCharactersScreen()
                }
        )
    }
    Surface(
        modifier = modifier
            .padding(horizontal = 2.dp)
            .height(120.dp),
        shape = MaterialTheme.shapes.small,
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
            text = "More Stats",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Normal,
            modifier = Modifier
                .alpha(0.7f)
        )
        Text(
            text = "Reviews",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .alpha(0.7f)
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
                            review = review.review,
                            onNavigateToSingleReview = {
                                onNavigateToSingleReview(it)
                            }
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
    modifier: Modifier = Modifier,
    onNavigateToStaffScreen: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Staff",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .alpha(0.7f)
                .align(Alignment.CenterEnd)
                .padding(horizontal = 16.dp)
                .clickable {
                    onNavigateToStaffScreen()
                }
        )
    }
    Surface(
        modifier = modifier
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
    modifier: Modifier = Modifier,
    onNavigateToRecommendationsScreen: () -> Unit,
    onNavigateToRecommendation: (Int) -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Recommendations",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End,
            modifier = Modifier
                .alpha(0.7f)
                .align(Alignment.CenterEnd)
                .padding(horizontal = 12.dp)
                .clickable {
                    onNavigateToRecommendationsScreen()
                }
        )
    }
    Surface(
        modifier = modifier
            .padding(horizontal = 4.dp),
        shape = MaterialTheme.shapes.small,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    ) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(recommendations) { recommendation ->
                if(recommendations.indexOf(recommendation) < 5){
                    RecommendationCard(
                        recommendation = recommendation,
                        onNavigateToRecommendation = {
                            onNavigateToRecommendation(it)
                        }
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
                style = MaterialTheme.typography.titleSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
            )
        },
        modifier = modifier.padding(
            start = 4.dp,
            end = 2.dp
        ),
        shape = MaterialTheme.shapes.medium,
    )
}