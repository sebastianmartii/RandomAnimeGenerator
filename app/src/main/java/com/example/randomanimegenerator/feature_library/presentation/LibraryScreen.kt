package com.example.randomanimegenerator.feature_library.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString

@Composable
fun LibraryScreen(
    state: LibraryState,
    statusList: List<LibraryStatus>,
    sortList: List<LibrarySortType>,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    onEvent: (LibraryEvent) -> Unit,
    focusRequester: FocusRequester,
    onNavigateToDetailsScreen: (Int) -> Unit
) {
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
                    onClearTextField = {
                        onEvent(LibraryEvent.ClearTextField)
                    },
                    focusRequester = focusRequester
                )
            } else {
                RegularTopAppBar(
                    title = state.type.toTypeString(),
                    onSearch = {
                        onEvent(LibraryEvent.Search)
                    }
                )
            }
        },
        modifier = modifier
            .padding(paddingValues)
    ) {values ->
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(values)
        ) {
            item(span = { GridItemSpan(3) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    statusList.onEach { item ->
                        CustomStatusFilterChip(
                            text = item,
                            selected = item.name == state.libraryStatus.name,
                            onSelect = {
                                onEvent(LibraryEvent.ChangeStatus(it))
                            }
                        )
                    }
                    sortList.onEach { item ->
                        CustomSortFilterChip(
                            text = item,
                            selected = item.name == state.librarySortType.name,
                            onSelect = {
                                onEvent(LibraryEvent.ChangeSortType(it))
                            }
                        )
                    }
                }
            }
            items(state.content) {
                LibraryCard(
                    title = it.title,
                    imageUrl = it.imageUrl,
                    modifier = Modifier.clickable {
                        onNavigateToDetailsScreen(it.malId)
                    }
                )
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
                    .height(160.dp)
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
            ){
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
private fun CustomStatusFilterChip(
    text: LibraryStatus,
    selected: Boolean,
    onSelect: (LibraryStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = { onSelect(text) },
        label = { Text(text = text.name) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        modifier = modifier.padding(
            start = 4.dp,
            top = 1.dp,
            bottom = 1.dp,
            end = 2.dp
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegularTopAppBar(
    title: String,
    onSearch: () -> Unit
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
                    contentDescription = "Search button"
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
    onClearTextField: () -> Unit,
    focusRequester: FocusRequester
) {
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
                        text = "Search...",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.alpha(0.7f)
                    )
                },
                trailingIcon = {
                    IconButton(onClick = onClearTextField) {
                        Icon(
                            imageVector = Icons.Default.Cancel,
                            contentDescription = "Clear Text Field"
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
                    contentDescription = "Stop Searching"
                )
            }
        },
        actions = {
            IconButton(onClick = onSearch) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search button"
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
private fun CustomSortFilterChip(
    text: LibrarySortType,
    selected: Boolean,
    onSelect: (LibrarySortType) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = { onSelect(text) },
        label = { Text(text = text.name) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(FilterChipDefaults.IconSize)
                )
            }
        } else {
            null
        },
        modifier = modifier.padding(
            start = 4.dp,
            top = 1.dp,
            bottom = 1.dp,
            end = 2.dp
        )
    )
}
