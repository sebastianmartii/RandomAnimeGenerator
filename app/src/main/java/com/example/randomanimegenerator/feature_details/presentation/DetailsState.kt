package com.example.randomanimegenerator.feature_details.presentation

import com.example.randomanimegenerator.feature_details.domain.model.AdditionalInfo
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_generator.presentation.Type

data class DetailsState(
    val imageUrl: String = "",
    val malId: Int = 0,
    val title: String = "",
    val description: String = "",
    val characters: List<Character> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val staff: List<Staff> = emptyList(),
    val recommendation: List<Recommendation> = emptyList(),
    val additionalInfo: List<AdditionalInfo> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val getRecommendations: Boolean = true,
    val getStaff: Boolean = true,
    val type: Type = Type.ANIME
)

