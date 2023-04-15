package com.example.randomanimegenerator.feature_generator.data.remote.anime_dto

import com.example.randomanimegenerator.core.databse.entities.GeneratorEntity

data class AnimeDto(
    val `data`: Data
) {
    fun toGeneratorEntity(): GeneratorEntity {
        return GeneratorEntity(
            title = data.title,
            malId = data.mal_id,
            imageUrl = data.images.jpg.image_url
        )
    }
}