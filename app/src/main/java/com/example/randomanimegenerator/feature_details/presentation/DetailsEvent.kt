package com.example.randomanimegenerator.feature_details.presentation

import coil.memory.MemoryCache
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

sealed interface DetailsEvent {
    data class GenerateRecommendationsAndStaff(val type: Type) : DetailsEvent
    data class AddOrRemoveFromFavorites(val isFavorite: Boolean) : DetailsEvent
    data class SelectStatus(val status: LibraryStatus) : DetailsEvent
    data class NavigateToSingleReview(val review: Review) : DetailsEvent
    object NavigateBackFromSingleReview : DetailsEvent
    data class ShowPopUpImage(val placeholder: MemoryCache.Key?) : DetailsEvent
    object ExpandSynopsis : DetailsEvent
}