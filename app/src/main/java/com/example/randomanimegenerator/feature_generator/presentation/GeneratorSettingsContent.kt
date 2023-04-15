package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.randomanimegenerator.core.constants.listOfAmounts
import com.example.randomanimegenerator.core.constants.listOfScores
import com.example.randomanimegenerator.core.constants.listOfTypes

@Composable
fun GeneratorSettingsContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    typeSelected: String,
    onTypeSelect: (String) -> Unit,
    scoreSelected: String,
    onScoreSelect: (String) -> Unit,
    amountSelected: String,
    onAmountSelect: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
    ) {
        ChipGroup(
            chipGroupTitle = "Type",
            selected = typeSelected,
            onSelect = onTypeSelect,
            listOfItems = listOfTypes,
            modifier = Modifier.padding(top = 32.dp)
        )
        ChipGroup(
            chipGroupTitle = "Minimum Score",
            selected = scoreSelected,
            listOfItems = listOfScores,
            onSelect = onScoreSelect
        )
        ChipGroup(
            chipGroupTitle = "Generated Amount",
            selected = amountSelected,
            listOfItems = listOfAmounts,
            onSelect = onAmountSelect
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
            bottom = 2.dp
        )
    )
    Divider(color = MaterialTheme.colorScheme.outlineVariant)
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
