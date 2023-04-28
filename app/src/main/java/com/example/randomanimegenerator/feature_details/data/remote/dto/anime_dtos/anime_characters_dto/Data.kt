package com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto

data class Data(
    val character: Character,
    val role: String,
    val voice_actors: List<VoiceActor>
)