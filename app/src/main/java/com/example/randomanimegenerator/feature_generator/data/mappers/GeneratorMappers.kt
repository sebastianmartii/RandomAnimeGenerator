package com.example.randomanimegenerator.feature_generator.data.mappers

import com.example.randomanimegenerator.core.database.entities.LibraryEntity
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.AnimeListDto
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Data
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.MangaData
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.MangaListDto
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

fun GeneratorModel.toLibraryEntity(): LibraryEntity {
    return LibraryEntity(
        malId = malId,
        titleEng = titleEng,
        titleJap = titleJap,
        type = type,
        studio = studio,
        imageUrl = imageUrl,
        source = source,
        score = score,
        rating = rating,
        synopsis = synopsis,
        status = status,
        year = year,
        genrePrimary = genrePrimary,
        genreSecondary = genreSecondary,
        genreTertiary = genreTertiary,
        demographic = demographic,
        episodes = episodes,
        authorPrimary = authorPrimary,
        authorSecondary = authorSecondary,
        volumes = volumes,
        libraryStatus = libraryStatus,
        libraryType = libraryType
    )
}

fun AnimeListDto.toListOfGeneratorModel(): List<GeneratorModel> = data.map { it.dataToGeneratorModel() }

fun Data.dataToGeneratorModel(): GeneratorModel {
    return GeneratorModel(
        titleEng = title,
        imageUrl = images.jpg.image_url,
        malId = mal_id,
        synopsis = synopsis
    )
}

fun MangaListDto.toListOfGeneratorModel(): List<GeneratorModel> = data.map { it.dataToGeneratorModel() }


fun MangaData.dataToGeneratorModel(): GeneratorModel {
    return GeneratorModel(
        titleEng = title,
        imageUrl = images.jpg.image_url,
        malId = mal_id,
        synopsis = synopsis

    )
}


