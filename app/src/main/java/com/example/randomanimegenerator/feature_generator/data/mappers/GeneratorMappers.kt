package com.example.randomanimegenerator.feature_generator.data.mappers

import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.AnimeListDto
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Data
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.MangaData
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.MangaListDto
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel


fun AnimeListDto.toListOfGeneratorModel(): List<GeneratorModel> = data.map { it.dataToGeneratorModel() }

fun Data.dataToGeneratorModel(): GeneratorModel {
    return GeneratorModel(
        title = title,
        imageUrl = images.jpg.image_url
    )
}

fun MangaListDto.toListOfGeneratorModel(): List<GeneratorModel> = data.map { it.dataToGeneratorModel() }


fun MangaData.dataToGeneratorModel(): GeneratorModel {
    return GeneratorModel(
        title = title,
        imageUrl = images.jpg.image_url
    )
}


