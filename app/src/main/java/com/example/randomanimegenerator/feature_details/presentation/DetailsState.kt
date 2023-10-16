package com.example.randomanimegenerator.feature_details.presentation

import coil.memory.MemoryCache
import com.example.randomanimegenerator.feature_details.domain.model.AdditionalInfo
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

data class DetailsState(
    val imageUrl: String = "",
    val largeImageUrl: String = "",
    val malId: Int = 0,
    val title: String = "",
    val description: String = "",
    val authors: String = "",
    val studios: String = "",
    val characters: List<Character> = emptyList(),
    val reviews: List<Review> = emptyList(),
    val spectatedReview: Review? = null,
    val staff: List<Staff> = emptyList(),
    val recommendation: List<Recommendation> = emptyList(),
    val additionalInfo: List<AdditionalInfo> = emptyList(),
    val isFavorite: Boolean = false,
    val isLoading: Boolean = true,
    val mainInfoResult: Result = Result.LOADING,
    val charactersResult: Result = Result.LOADING,
    val reviewsResult: Result = Result.LOADING,
    val staffResult: Result = Result.LOADING,
    val recommendationsResult: Result = Result.LOADING,
    val getRecommendationsAndStaff: Boolean = true,
    val type: Type = Type.ANIME,
    val libraryStatus: LibraryStatus = LibraryStatus.PLANNING,
    val showPopUp: Boolean = false,
    val synopsisExpanded: Boolean = false,
    val entryId: Int = 0,
    val popUpPlaceholder: MemoryCache.Key? = null
)

enum class Result {
    SUCCESS, LOADING, ERROR
}
