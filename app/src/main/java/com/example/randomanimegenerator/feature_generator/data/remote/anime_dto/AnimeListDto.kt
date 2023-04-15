package com.example.randomanimegenerator.feature_generator.data.remote.anime_dto

import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

data class AnimeListDto(
    val `data`: List<Data>
) {
    fun toIdList(): List<Int> = data.map { it.mal_id }

    fun toListOfGeneratorModel(): List<GeneratorModel> = data.map { it.dataToGeneratorModel() }
}