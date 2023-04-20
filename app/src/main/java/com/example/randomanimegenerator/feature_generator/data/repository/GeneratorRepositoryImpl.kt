package com.example.randomanimegenerator.feature_generator.data.repository

import com.example.randomanimegenerator.core.database.daos.LibraryDao
import com.example.randomanimegenerator.core.database.entities.LibraryEntity
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.data.mappers.toListOfGeneratorModel
import com.example.randomanimegenerator.feature_generator.data.remote.GeneratorApi
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import com.example.randomanimegenerator.feature_generator.domain.repository.GeneratorRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class GeneratorRepositoryImpl(
    private val generatorApi: GeneratorApi,
    private val libraryDao: LibraryDao,
) : GeneratorRepository {

    override fun generateAnime(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>> = flow {
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

    override fun generateManga(page: Int, minScore: Int): Flow<Resource<List<GeneratorModel>>> = flow {
        emit(Resource.Loading())

        try {
            val remoteMangaList = generatorApi.getAllManga(minScore = minScore, page = page)
            if (remoteMangaList.isSuccessful) {
                emit(Resource.Success(data = remoteMangaList.body()?.toListOfGeneratorModel()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "error"))
        }
    }

    override suspend fun addToLibrary(content: LibraryEntity) {
        libraryDao.insert(content)
    }


}
