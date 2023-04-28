package com.example.randomanimegenerator.feature_details.domain.use_cases

data class DetailsUseCases(
    val getInfoUseCase: GetInfoUseCase,
    val getCharactersUseCase: GetCharactersUseCase,
    val getRecommendationsUseCase: GetRecommendationsUseCase,
    val getReviewsUseCase: GetReviewsUseCase,
    val getStaffUseCase: GetStaffUseCase
)
