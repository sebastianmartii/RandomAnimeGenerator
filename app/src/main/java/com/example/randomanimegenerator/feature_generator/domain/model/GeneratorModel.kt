package com.example.randomanimegenerator.feature_generator.domain.model

data class GeneratorModel(
    val titleEng: String = "",
    val titleJap: String? = "",
    val imageUrl: String = "",
    val malId: Int = 0,
    val type: String? = "",
    val studio: String? = "",
    val authorPrimary: String? = "",
    val authorSecondary: String? = null,
    val source: String? = "",
    val score: String? = "",
    val rating: String? = "",
    val synopsis: String? = "",
    val status: String? = "",
    val year: Int? = 0,
    val genrePrimary: String? = "",
    val genreSecondary: String? = null,
    val genreTertiary: String? = null,
    val demographic: String? = "",
    val episodes: String? = "",
    val volumes: Int? = 0,
    val libraryStatus: String = "planned",
    val libraryType: String = "anime"
)
