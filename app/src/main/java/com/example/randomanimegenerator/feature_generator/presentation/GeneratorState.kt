package com.example.randomanimegenerator.feature_generator.presentation

import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

data class GeneratorState(
    val listOfItems: List<GeneratorModel> = emptyList(),
    val isLoading: Boolean = false,
    val hasGenerated: Boolean = false,
    val typeSelected: Type = Type.ANIME,
    val scoreSelected: String = "7",
    val amountSelected: Amount = Amount.ONE
)
