package com.example.randomanimegenerator.feature_generator.presentation

import android.os.Parcelable
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeneratorState(
    val listOfItems: List<GeneratorModel> = emptyList(),
    val isLoading: Boolean = false,
    val editGeneratingParams: Boolean = false,
    val typeSelected: Type = Type.ANIME,
    val scoreSelected: String = "8",
    val amountSelected: Amount = Amount.ONE
) : Parcelable
