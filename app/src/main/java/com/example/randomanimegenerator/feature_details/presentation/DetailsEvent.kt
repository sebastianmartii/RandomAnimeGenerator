package com.example.randomanimegenerator.feature_details.presentation

sealed interface DetailsEvent {
    object GenerateStaff : DetailsEvent
    object GenerateRecommendations : DetailsEvent
    data class AddToLibrary(val isFavorite: Boolean) : DetailsEvent
}