package com.example.randomanimegenerator.feature_details.domain.model

import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

data class MainModel(
    // manga only
    val authors: String,
    val chapters: Int,
    // anime only
    val source: String,
    val episodes: Int,
    val studios: String,
    // shared
    val malId: Int,
    val title: String,
    val imageUrl: String,
    val largeImageUrl: String,
    val synopsis: String,
    val type: String,
    val status: String,
    val score: Double,
    val genres: String,
    val themes: String,
    val demographic: String,
    val isLoading: Boolean,
    val isFavorite: Boolean,
    val libraryStatus: LibraryStatus
) {

    private val animeStatusList = listOf(
        AdditionalInfo(statusName = "Format", status = type),
        AdditionalInfo(statusName = "Status", status = status),
        AdditionalInfo(statusName = "Source", status = source),
        AdditionalInfo(statusName = "Episodes", status = "$episodes"),
        AdditionalInfo(statusName = "Score", status = "$score"),
        AdditionalInfo(statusName = "Genres", status = genres),
        AdditionalInfo(statusName = "Themes", status = themes),
        AdditionalInfo(statusName = "Demographic", status = demographic),
    )

    private val mangaStatusList = listOf(
        AdditionalInfo(statusName = "Format", status = type),
        AdditionalInfo(statusName = "Status", status = status),
        AdditionalInfo(statusName = "Chapters", status = "$chapters"),
        AdditionalInfo(statusName = "Score", status = "$score"),
        AdditionalInfo(statusName = "Genres", status = genres),
        AdditionalInfo(statusName = "Themes", status = themes),
        AdditionalInfo(statusName = "Demographic", status = demographic),
    )

    val statusList: List<AdditionalInfo> = if (type == "Manga") mangaStatusList else animeStatusList
}
