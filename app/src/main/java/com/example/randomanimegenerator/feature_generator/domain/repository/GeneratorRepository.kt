package com.example.randomanimegenerator.feature_generator.domain.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import kotlinx.coroutines.flow.Flow

interface GeneratorRepository {


    fun generateAnime(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>>

    fun generateAnimeList(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>>

    fun generateManga(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>>

    fun generateMangaList(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>>
}