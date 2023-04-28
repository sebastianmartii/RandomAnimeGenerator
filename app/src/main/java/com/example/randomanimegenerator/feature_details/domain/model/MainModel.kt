package com.example.randomanimegenerator.feature_details.domain.model

data class MainModel(
    // manga only
    val authors: List<String>,
    val chapters: Int,
    // anime only
    val source: String,
    val episodes: Int,
    val studios: List<String>,
    // shared
    val malId: Int,
    val title: String,
    val imageUrl: String,
    val synopsis: String,
    val type: String,
    val status: String,
    val score: Double,
    val genres: List<String>,
    val themes: List<String>,
    val demographic: List<String>,
    val isLoading: Boolean
) {

    private val animeStatusList = listOf(
        AdditionalInfo(statusName = "Format", status = type),
        AdditionalInfo(statusName = "Status", status = status),
        AdditionalInfo(statusName = "Studios", status = studios.joinToString(separator = ", ")),
        AdditionalInfo(statusName = "Source", status = source),
        AdditionalInfo(statusName = "Episodes", status = "$episodes"),
        AdditionalInfo(statusName = "Score", status = "$score"),
        AdditionalInfo(statusName = "Genres", status = genres.joinToString(separator = ", ")),
        AdditionalInfo(statusName = "Themes", status = themes.joinToString(separator = ", ")),
        AdditionalInfo(statusName = "Demographic", status = demographic.joinToString(separator = ", ")),
    )

    private val mangaStatusList = listOf(
        AdditionalInfo(statusName = "Format", status = type),
        AdditionalInfo(statusName = "Status", status = status),
        AdditionalInfo(statusName = "Authors", status = authors.joinToString(separator = ", ")),
        AdditionalInfo(statusName = "Chapters", status = "$chapters"),
        AdditionalInfo(statusName = "Score", status = "$score"),
        AdditionalInfo(statusName = "Genres", status = genres.joinToString(separator = ", ")),
        AdditionalInfo(statusName = "Themes", status = themes.joinToString(separator = ", ")),
        AdditionalInfo(statusName = "Demographic", status = demographic.joinToString(separator = ", ")),
    )

    val statusList: List<AdditionalInfo> = if (type == "Manga") mangaStatusList else animeStatusList
}
