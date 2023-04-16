package com.example.randomanimegenerator.feature_generator.domain.use_cases

import com.example.randomanimegenerator.core.constants.minScoreToPageMapAnime
import com.example.randomanimegenerator.core.constants.minScoreToPageMapManga
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import com.example.randomanimegenerator.feature_generator.domain.repository.GeneratorRepository
import com.example.randomanimegenerator.feature_generator.presentation.Type
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random

class GenerateListUseCase(
    private val repository: GeneratorRepository
) {
    operator fun invoke(type: Type, minScore: Int): Flow<Resource<List<GeneratorModel>>> {
        return when(type) {
            Type.ANIME -> repository.generateAnimeList(page = Random.nextInt(minScoreToPageMapAnime[minScore]!!), minScore = minScore)
            Type.MANGA -> repository.generateMangaList(page = Random.nextInt(minScoreToPageMapManga[minScore]!!), minScore = minScore)
        }
    }
}