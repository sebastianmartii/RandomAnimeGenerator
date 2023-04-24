package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomanimegenerator.core.constants.listOfAmounts
import com.example.randomanimegenerator.core.constants.listOfScores
import com.example.randomanimegenerator.core.constants.listOfTypes


@Composable
fun GeneratorSettings(
    paddingValues: PaddingValues,
    state: GeneratorState,
    onEvent: (GeneratorEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        ChipGroup(
            chipGroupTitle = "Type",
            selected = state.typeSelected.toTypeString(),
            listOfItems = listOfTypes,
            onSelect = {
                onEvent(GeneratorEvent.SetType(it.toType()))
            },
            modifier = Modifier.padding(top = 32.dp)
        )
        ChipGroup(
            chipGroupTitle = "Minimum Score",
            selected = state.scoreSelected,
            listOfItems = listOfScores,
            onSelect = {
                onEvent(GeneratorEvent.SetScore(it))
            }
        )
        ChipGroup(
            chipGroupTitle = "Generated Amount",
            selected = state.amountSelected.toAmountString(),
            listOfItems = listOfAmounts,
            onSelect = {
                onEvent(GeneratorEvent.SetAmount(it.toAmount()))
            }
        )
    }
}


@Composable
private fun ChipGroup(
    modifier: Modifier = Modifier,
    chipGroupTitle: String,
    selected: String,
    listOfItems: List<String>,
    onSelect: (String) -> Unit
) {
    Text(
        text = chipGroupTitle,
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier.padding(
            start = 8.dp,
            bottom = 2.dp,
            top = 4.dp
        )
    )
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        items(listOfItems) {itemName ->
            CustomFilterChip(
                text = itemName,
                selected = selected == itemName,
                onSelect = onSelect
            )
        }
    }
    Divider(color = MaterialTheme.colorScheme.outlineVariant)
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
            start = 8.dp,
            top = 4.dp,
            bottom = 4.dp,
            end = 4.dp
        )
    )
}
