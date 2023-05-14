package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.feature_details.data.mappers.toReviews
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class ReviewsViewModel @Inject constructor(
    db: RandomAnimeGeneratorDb,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    private val type = savedStateHandle.get<String>("type")

    val reviews = db.reviewDao.getReviewsAsFlow(id!!, type!!).map { it.toReviews() }
}