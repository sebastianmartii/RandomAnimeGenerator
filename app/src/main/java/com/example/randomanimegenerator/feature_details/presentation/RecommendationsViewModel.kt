package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.feature_details.data.mappers.toRecommendations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class RecommendationsViewModel @Inject constructor(
    db: RandomAnimeGeneratorDb,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    val type = savedStateHandle.get<String>("type")

    val recommendations = db.recommendationDao.getRecommendationsAsFlow(id!!, type!!).map { it.toRecommendations() }
}