package com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.manga_characters_dto

data class Character(
    val images: Images,
    val mal_id: Int,
    val name: String,
    val url: String
)