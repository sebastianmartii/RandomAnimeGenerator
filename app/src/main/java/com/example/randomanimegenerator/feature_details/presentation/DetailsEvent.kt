package com.example.randomanimegenerator.feature_details.presentation

import coil.memory.MemoryCache
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

sealed interface DetailsEvent {
    data class GenerateRecommendationsAndStaff(val type: Type) : DetailsEvent
    data class AddOrRemoveFromFavorites(val isFavorite: Boolean) : DetailsEvent
    data class SelectStatus(val status: LibraryStatus) : DetailsEvent
    data class NavigateToDestination(val destination: String) : DetailsEvent
    data class NavigateToSingleReview(
        val destination: String,
        val author: String,
        val score: Int,
        val review: String
    ) : DetailsEvent

    data class NavigateToRecommendation(val destination: String, val malId: Int) : DetailsEvent
    object NavigateBack : DetailsEvent
    data class ShowPopUpImage(val placeholder: MemoryCache.Key?) : DetailsEvent
    object ExpandSynopsis : DetailsEvent
}