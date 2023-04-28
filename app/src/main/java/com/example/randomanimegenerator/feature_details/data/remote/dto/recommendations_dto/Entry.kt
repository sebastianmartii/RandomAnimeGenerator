package com.example.randomanimegenerator.feature_details.data.remote.dto.recommendations_dto

data class Entry(
    val images: Images,
    val mal_id: Int,
    val title: String,
    val url: String
)