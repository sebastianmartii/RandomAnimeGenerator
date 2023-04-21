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
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString

@Composable
fun AnimeLibraryScreen(
    paddingValues: PaddingValues,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.setType(Type.ANIME)
    }
    val state by viewModel.state.collectAsStateWithLifecycle(LibraryState())
    LibraryScreen(
        state = state,
        paddingValues = paddingValues,
        statusList = animeStatusList,
        onSelect = {
            viewModel.selectStatus(it)
        }
    )
}

@Composable
fun MangaLibraryScreen(
    paddingValues: PaddingValues,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        viewModel.setType(Type.MANGA)
    }
    val state by viewModel.state.collectAsStateWithLifecycle(LibraryState())
    LibraryScreen(
        state = state,
        paddingValues = paddingValues,
        statusList = mangaStatusList,
        onSelect = {
            viewModel.selectStatus(it)
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LibraryScreen(
    state: LibraryState,
    statusList: List<LibraryStatus>,
    paddingValues: PaddingValues,
    onSelect: (String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.type.toTypeString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            )
        },
        modifier = Modifier.padding(paddingValues)
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
                        CustomFilterChip(
                            text = item.name,
                            selected = item.name == state.libraryStatus,
                            onSelect = {
                                onSelect(it)
                            }
                        )
                    }
                }
            }
            items(state.content) {
                LibraryCard(title = it.title, imageUrl = it.imageUrl)
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
            .padding(4.dp)
            .clickable { },
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
private fun CustomFilterChip(
    text: String,
    selected: Boolean,
    onSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = selected,
        onClick = { onSelect(text) },
        label = { Text(text = text) },
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