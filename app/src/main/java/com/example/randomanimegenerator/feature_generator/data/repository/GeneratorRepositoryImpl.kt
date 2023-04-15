package com.example.randomanimegenerator.feature_generator.data.repository

import com.example.randomanimegenerator.core.databse.daos.GeneratorDao
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.data.remote.GeneratorApi
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.AnimeListDto
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import com.example.randomanimegenerator.feature_generator.domain.repository.GeneratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GeneratorRepositoryImpl(
    private val generatorApi: GeneratorApi,
    private val generatorDao: GeneratorDao
) : GeneratorRepository {

    override fun generateAnime(malId: Int): Flow<Resource<GeneratorModel>> = flow {
        emit(Resource.Loading())

        try {
            val remoteAnime = generatorApi.getRandomAnime(malId)
            generatorDao.insert(remoteAnime.toGeneratorEntity())
        } catch (e: IOException) {
            emit(Resource.Error(message = "error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error"))
        }

        val newAnime = generatorDao.getGeneratorEntity(malId = malId)
        emit(Resource.Success(data = newAnime.toGeneratorModel()))
    }

    override fun generateAnimeList(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteAnimeList = generatorApi.getAllAnime(minScore = minScore, page = page)
            if (remoteAnimeList.isSuccessful) {
                emit(Resource.Success(data = remoteAnimeList.body()?.toListOfGeneratorModel()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error"))
        }
    }

    override suspend fun generateAnimeIds(page: Int, minScore: Int): AnimeListDto = generatorApi.getAllAnime(minScore = minScore, page = page).body()!!
}