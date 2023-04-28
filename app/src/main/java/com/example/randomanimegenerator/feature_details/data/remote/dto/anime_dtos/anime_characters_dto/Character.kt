package com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto

data class Character(
    val images: Images,
    val mal_id: Int,
    val name: String,
    val url: String
)