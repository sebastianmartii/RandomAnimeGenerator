package com.example.randomanimegenerator.feature_library.presentation

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.core.navigation.BottomNavItem
import com.example.randomanimegenerator.core.navigation.BottomNavigationBar
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.data.mappers.toSortTypeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeLibraryScreen(
    state: LibraryState,
    bottomNavItems: List<BottomNavItem>,
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    eventFlow: Flow<LibraryViewModel.UiEvent>,
    scaffoldState: BottomSheetScaffoldState,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (id: Int, type: String) -> Unit,
    onNavigateToBottomNavItem: (String) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent(
                statusList = statusList,
                sortList = sortList,
                filterType = state.filterType,
                libraryStatus = state.libraryStatus,
                librarySortType = state.librarySortType,
                onEvent = onEvent
            )
        }
    ) { paddingValues ->
        LibraryScreen(
            state = state,
            bottomNavItems = bottomNavItems,
            paddingValues = paddingValues,
            eventFlow = eventFlow,
            scaffoldState = scaffoldState,
            onEvent = onEvent,
            focusRequester = focusRequester,
            onNavigateToDetailsScreen = onNavigateToDetailsScreen,
            onNavigateToBottomNavItem = onNavigateToBottomNavItem
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MangaLibraryScreen(
    state: LibraryState,
    bottomNavItems: List<BottomNavItem>,
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    eventFlow: Flow<LibraryViewModel.UiEvent>,
    scaffoldState: BottomSheetScaffoldState,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (id: Int, type: String) -> Unit,
    onNavigateToBottomNavItem: (String) -> Unit
) {
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            SheetContent(
                statusList = statusList,
                sortList = sortList,
                filterType = state.filterType,
                libraryStatus = state.libraryStatus,
                librarySortType = state.librarySortType,
                onEvent = onEvent
            )
        }
    ) { paddingValues ->
        LibraryScreen(
            state = state,
            bottomNavItems = bottomNavItems,
            paddingValues = paddingValues,
            eventFlow = eventFlow,
            scaffoldState = scaffoldState,
            onEvent = onEvent,
            focusRequester = focusRequester,
            onNavigateToDetailsScreen = onNavigateToDetailsScreen,
            onNavigateToBottomNavItem = onNavigateToBottomNavItem
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryScreen(
    state: LibraryState,
    paddingValues: PaddingValues,
    bottomNavItems: List<BottomNavItem>,
    eventFlow: Flow<LibraryViewModel.UiEvent>,
    scaffoldState: BottomSheetScaffoldState,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (id: Int, type: String) -> Unit,
    onNavigateToBottomNavItem: (String) -> Unit
) {
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when(event) {
                LibraryViewModel.UiEvent.CloseFilterMenu -> {
                    scaffoldState.bottomSheetState.hide()
                }
                LibraryViewModel.UiEvent.OpenFilterMenu -> {
                    scaffoldState.bottomSheetState.expand()
                }
            }
        }
    }
    Scaffold(
        topBar = {
            if (state.isSearching) {
                SearchTopAppBar(
                    searchText = state.searchText,
                    onSearchTextChanges = {
                        onEvent(LibraryEvent.ChangeSearchText(it))
                    },
                    onSearch = {
                        onEvent(LibraryEvent.Search)
                    },
                    onChangeSheetState = {
                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                            onEvent(LibraryEvent.CloseFilterMenu)
                        } else {
                            onEvent(LibraryEvent.OpenFilterMenu)
                        }
                    },
                    onClearTextField = {
                        onEvent(LibraryEvent.ClearTextField)
                    },
                    focusRequester = focusRequester,
                    expanded = scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded
                )
            } else {
                RegularTopAppBar(
                    title = state.type.toTypeString(),
                    onSearch = {
                        onEvent(LibraryEvent.Search)
                    },
                    onChangeSheetState = {
                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                            onEvent(LibraryEvent.CloseFilterMenu)
                        } else {
                            onEvent(LibraryEvent.OpenFilterMenu)
                        }
                    }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                selectedItemIndex = if (state.type == Type.ANIME) 1 else 2,
                onItemClick = onNavigateToBottomNavItem
            )
        },
        modifier = Modifier.padding(paddingValues)
    ) { values ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .then(
                    if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                        val interactionSource = remember { MutableInteractionSource() }
                        Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            onEvent(LibraryEvent.CloseFilterMenu)
                        }
                    } else {
                        Modifier
                    }
                )
                .fillMaxHeight()
                .padding(values)
        ) {
            items(
                state.content,
                key = { libraryModel ->
                    libraryModel.malId
                }
            ) {
                LibraryCard(
                    title = it.title,
                    imageUrl = it.imageUrl,
                    modifier = Modifier.clickable {
                        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
                            onEvent(LibraryEvent.CloseFilterMenu)
                            return@clickable
                        }
                        onNavigateToDetailsScreen(it.malId, state.type.toTypeString())
                    }
                )
            }
        }
    }
}


@Composable
fun SheetContent(
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    filterType: FilterType,
    libraryStatus: LibraryStatus,
    librarySortType: LibrarySortType,
    modifier: Modifier = Modifier,
    onEvent: (LibraryEvent) -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        onEvent(LibraryEvent.ChangeFilterType(FilterType.FILTER))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.filter),
                    )
                }
                TextButton(
                    onClick = {
                        onEvent(LibraryEvent.ChangeFilterType(FilterType.SORT))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = stringResource(id = R.string.sort),
                    )
                }
            }
            AnimatedContent(
                targetState = filterType,
                transitionSpec = {
                    when(targetState) {
                        FilterType.FILTER -> {
                            (slideInHorizontally(
                                keyframes {
                                    durationMillis = 150
                                }
                            ) { fullWidth -> fullWidth } + fadeIn()).togetherWith(
                                slideOutHorizontally(
                                    keyframes {
                                        durationMillis = 150
                                    }
                                ) { fullWidth -> -fullWidth } + fadeOut())
                        }
                        FilterType.SORT -> {
                            (slideInHorizontally(
                                keyframes {
                                    durationMillis = 150
                                }
                            ) { fullWidth -> -fullWidth } + fadeIn()).togetherWith(
                                slideOutHorizontally(
                                    keyframes {
                                        durationMillis = 150
                                    }
                                ) { fullWidth -> fullWidth } + fadeOut())
                        }
                    }.using(
                        SizeTransform(clip = false)
                    )

                },
                label = ""
            ) {filterType ->
                when(filterType) {
                    FilterType.FILTER -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier
                                    .padding(start = 4.dp, bottom = 2.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.inversePrimary)
                                        .clip(MaterialTheme.shapes.large)
                                        .fillMaxWidth(0.5f)
                                        .height(4.dp),
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp, top = 2.dp)
                            )
                            statusList.onEach { status ->
                                FilterItem(
                                    status = status,
                                    selected = status.name == libraryStatus.name,
                                    onSelect = {
                                        onEvent(LibraryEvent.ChangeStatus(it))
                                    }
                                )
                            }
                        }
                    }
                    FilterType.SORT -> {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier
                                    .padding(end = 4.dp, bottom = 2.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.inversePrimary)
                                        .clip(MaterialTheme.shapes.large)
                                        .fillMaxWidth(0.5f)
                                        .height(4.dp)
                                )
                            }
                            HorizontalDivider(
                                modifier = Modifier
                                    .height(2.dp)
                                    .fillMaxWidth()
                            )
                            sortList.onEach { sortType ->
                                SortItem(
                                    sortType = sortType,
                                    selected = sortType.name == librarySortType.name,
                                    onSelect = {
                                        onEvent(LibraryEvent.ChangeSortType(it))
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun LibraryCard(
    title: String,
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(IntrinsicSize.Max)
            .padding(4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .build(),
                placeholder = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                error = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                fallback = ColorPainter(MaterialTheme.colorScheme.primaryContainer),
                contentDescription = title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .height(190.dp)
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
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegularTopAppBar(
    title: String,
    onSearch: () -> Unit,
    onChangeSheetState: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_button_text)
                )
            }
            IconButton(onClick = onChangeSheetState) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = stringResource(id = R.string.filter)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopAppBar(
    searchText: String,
    onSearchTextChanges: (String) -> Unit,
    onSearch: () -> Unit,
    onChangeSheetState: () -> Unit,
    onClearTextField: () -> Unit,
    focusRequester: FocusRequester,
    expanded: Boolean
) {
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
    TopAppBar(
        title = {
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    onSearchTextChanges(it)
                },
                textStyle = MaterialTheme.typography.titleMedium,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.ellipsis_search_text),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.alpha(0.7f)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchText.isBlank()) {
                            onSearch()
                        } else {
                            onClearTextField()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = stringResource(id = R.string.clear_text_field_text)
                        )
                    }
                },
                shape = MaterialTheme.shapes.large,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    disabledBorderColor = Color.Transparent,
                    errorBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                modifier = Modifier.focusRequester(focusRequester)
            )
        },
        navigationIcon = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.stop_searching_text)
                )
            }
        },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.search_button_text)
                )
            }
            IconButton(onClick = {
                onChangeSheetState()
                if (!expanded) {
                    focusManager.clearFocus()
                } else {
                    focusRequester.requestFocus()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.FilterList,
                    contentDescription = stringResource(id = R.string.filter)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        )
    )
}

@Composable
fun FilterItem(
    status: LibraryStatus,
    selected: Boolean,
    onSelect: (LibraryStatus) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                onSelect(status)
            }
    ) {
        Crossfade(targetState = selected, label = "") { selected ->
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.CheckBox,
                    contentDescription = stringResource(id = R.string.check_box)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.CheckBoxOutlineBlank,
                    contentDescription = stringResource(id = R.string.check_box)
                )
            }
        }
        Text(
            text = status.name.lowercase()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}

@Composable
fun SortItem(
    sortType: LibrarySortType,
    selected: Boolean,
    onSelect: (LibrarySortType) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable {
                onSelect(sortType)
            }
    ) {
        Crossfade(targetState = selected, label = "") { selected ->
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.CheckBox,
                    contentDescription = stringResource(id = R.string.check_box)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.CheckBoxOutlineBlank,
                    contentDescription = stringResource(id = R.string.check_box)
                )
            }
        }
        Text(
            text = sortType.toSortTypeString(),
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(start = 16.dp),
        )
    }
}
