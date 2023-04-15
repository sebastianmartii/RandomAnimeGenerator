package com.example.randomanimegenerator.feature_generator.domain.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.AnimeListDto
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import kotlinx.coroutines.flow.Flow

interface GeneratorRepository {

    suspend fun generateAnimeIds(page: Int, minScore: Int): AnimeListDto

    fun generateAnime(malId: Int): Flow<Resource<GeneratorModel>>

    fun generateAnimeList(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>>
}