package com.example.randomanimegenerator.feature_profile.presentation

sealed class ProfileFeatureUiEvent {
    data class ShowSnackBar(val message: String) : ProfileFeatureUiEvent()
}