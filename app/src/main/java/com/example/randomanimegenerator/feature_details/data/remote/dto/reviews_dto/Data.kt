package com.example.randomanimegenerator.feature_details.data.remote.dto.reviews_dto

data class Data(
    val date: String,
    val episodes_watched: Int,
    val is_preliminary: Any,
    val is_spoiler: Any,
    val mal_id: Int,
    val reactions: Reactions,
    val review: String,
    val score: Int,
    val tags: List<String>,
    val type: String,
    val url: String,
    val user: User
)