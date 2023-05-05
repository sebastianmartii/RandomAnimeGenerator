package com.example.randomanimegenerator.feature_details.presentation

sealed interface DetailsEvent {
    object GenerateStaff : DetailsEvent
    object GenerateRecommendations : DetailsEvent
    data class AddOrRemoveFromFavorites(val isFavorite: Boolean) : DetailsEvent
}