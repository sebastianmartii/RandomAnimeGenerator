package com.example.randomanimegenerator.feature_generator.data.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import com.example.randomanimegenerator.feature_generator.domain.repository.GeneratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGeneratorRepository : GeneratorRepository {

    override fun generateAnime(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>> = flow {
        emit(Resource.Success(data = listOf(GeneratorModel(
            titleEng = "",
            imageUrl = "",
            malId = 1,
            type = "",
            studio = "",
            authorPrimary = "",
            source = "",
            score = "",
            rating = "",
            synopsis = "",
            status = "",
            genrePrimary = "",
            demographic = "",
            episodes = "",
            libraryType = "Anime",
        ))))
    }

    override fun generateManga(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>> = flow {
        emit(Resource.Success(data = listOf(GeneratorModel(
            titleEng = "",
            imageUrl = "",
            malId = 1,
            type = "",
            studio = "",
            authorPrimary = "",
            source = "",
            score = "",
            rating = "",
            synopsis = "",
            status = "",
            genrePrimary = "",
            demographic = "",
            episodes = "",
            libraryType = "Manga",
        ))))
    }
}