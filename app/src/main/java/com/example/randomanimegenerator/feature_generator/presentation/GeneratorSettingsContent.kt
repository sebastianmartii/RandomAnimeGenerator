package com.example.randomanimegenerator.feature_generator.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.randomanimegenerator.R
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Tune,
                contentDescription = stringResource(id = R.string.leading_icon_text),
                modifier = Modifier
                    .size(80.dp)
                    .align(Alignment.Center),
            )
        }
        ChipGroup(
            chipGroupTitle = stringResource(id = R.string.type_text),
            selected = state.typeSelected.toTypeString(),
            listOfItems = listOfTypes,
            hint = stringResource(id = R.string.type_hint_text),
            onSelect = {
                onEvent(GeneratorEvent.SetType(it.toType()))
            }
        )
        ChipGroup(
            chipGroupTitle = stringResource(id = R.string.minimum_score_text),
            selected = state.scoreSelected,
            listOfItems = listOfScores,
            hint = stringResource(id = R.string.score_hint_text),
            onSelect = {
                onEvent(GeneratorEvent.SetScore(it))
            }
        )
        ChipGroup(
            chipGroupTitle = stringResource(id = R.string.generated_amount_text),
            selected = state.amountSelected.toAmountString(),
            listOfItems = listOfAmounts,
            hint = stringResource(id = R.string.amount_hint_text),
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
    hint: String,
    selected: String,
    listOfItems: List<String>,
    onSelect: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalAlignment = Alignment.Start
    ) {
        Divider(color = MaterialTheme.colorScheme.outlineVariant)
        Text(
            text = chipGroupTitle,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                start = 8.dp,
                top = 4.dp
            )
        )
        Text(
            text = hint,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.ExtraLight,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            listOfItems.onEach { itemName ->
                CustomFilterChip(
                    text = itemName,
                    selected = selected == itemName,
                    onSelect = onSelect
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
            start = 8.dp,
            top = 4.dp,
            bottom = 4.dp,
            end = 4.dp
        )
    )
}
