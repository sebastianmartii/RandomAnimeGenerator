@file:OptIn(ExperimentalMaterial3Api::class)

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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.data.mappers.toSortTypeString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun AnimeLibraryScreen(
    state: LibraryState,
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    paddingValues: PaddingValues,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (id: Int, type: String) -> Unit
) {
    LibraryScreen(
        state = state,
        statusList = statusList,
        sortList = sortList,
        paddingValues = paddingValues,
        scope = scope,
        scaffoldState = scaffoldState,
        onEvent = onEvent,
        focusRequester = focusRequester,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        modifier = modifier
    )
}

@Composable
fun MangaLibraryScreen(
    state: LibraryState,
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    paddingValues: PaddingValues,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (id: Int, type: String) -> Unit
) {
    LibraryScreen(
        state = state,
        statusList = statusList,
        sortList = sortList,
        paddingValues = paddingValues,
        scope = scope,
        scaffoldState = scaffoldState,
        onEvent = onEvent,
        focusRequester = focusRequester,
        onNavigateToDetailsScreen = onNavigateToDetailsScreen,
        modifier = modifier
    )
}

@Composable
private fun LibraryScreen(
    state: LibraryState,
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    paddingValues: PaddingValues,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    modifier: Modifier = Modifier,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (id: Int, type: String) -> Unit
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
        },
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
                        scope.launch {
                            if (scaffoldState.bottomSheetState.isVisible) {
                                scaffoldState.bottomSheetState.hide()
                            } else {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    },
                    onClearTextField = {
                        onEvent(LibraryEvent.ClearTextField)
                    },
                    focusRequester = focusRequester,
                    expanded = scaffoldState.bottomSheetState.isVisible
                )
            } else {
                RegularTopAppBar(
                    title = state.type.toTypeString(),
                    onSearch = {
                        onEvent(LibraryEvent.Search)
                    },
                    onChangeSheetState = {
                        scope.launch {
                            if (scaffoldState.bottomSheetState.isVisible) {
                                scaffoldState.bottomSheetState.hide()
                            } else {
                                scaffoldState.bottomSheetState.expand()
                            }
                        }
                    }
                )
            }
        },
        modifier = modifier
            .padding(paddingValues)
    ) { values ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .then(
                    if (scaffoldState.bottomSheetState.isVisible) {
                        val interactionSource = MutableInteractionSource()
                        Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            scope.launch {
                                scaffoldState.bottomSheetState.hide()
                            }
                        }
                    } else {
                        Modifier
                    }
                )
                .fillMaxHeight()
                .padding(values)
        ) {
            items(state.content) {
                LibraryCard(
                    title = it.title,
                    imageUrl = it.imageUrl,
                    modifier = Modifier.clickable {
                        if (scaffoldState.bottomSheetState.isVisible) {
                            scope.launch {
                                scaffoldState.bottomSheetState.hide()
                            }
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

                }
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
                            Divider(
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
                            Divider(
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
                model = imageUrl,
                contentDescription = title,
                modifier = Modifier
                    .height(190.dp)
                    .align(Alignment.TopCenter),
                contentScale = ContentScale.FillBounds
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
                    imageVector = Icons.Default.ArrowBack,
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
        Crossfade(targetState = selected) { selected ->
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
        Crossfade(targetState = selected) { selected ->
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
