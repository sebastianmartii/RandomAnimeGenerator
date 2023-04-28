package com.example.randomanimegenerator.feature_details.data.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.data.mappers.toCharacters
import com.example.randomanimegenerator.feature_details.data.mappers.toMainModel
import com.example.randomanimegenerator.feature_details.data.mappers.toRecommendations
import com.example.randomanimegenerator.feature_details.data.mappers.toReviews
import com.example.randomanimegenerator.feature_details.data.mappers.toStaff
import com.example.randomanimegenerator.feature_details.data.remote.DetailsApi
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.AnimeDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto.AnimeCharactersDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.MangaDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.manga_characters_dto.MangaCharactersDto
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.MainModel
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_generator.presentation.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DetailsRepositoryImpl(
    private val detailsApi: DetailsApi
): DetailsRepository {

    override fun getInfo(id: Int, type: Type): Flow<Resource<MainModel>> = flow {
        emit(Resource.Loading())

        try {
            val response = when(type) {
                Type.ANIME -> detailsApi.getAnime(id)
                Type.MANGA -> detailsApi.getManga(id)
            }
            if (response.isSuccessful) {
                when(type) {
                    Type.ANIME -> emit(Resource.Success(data = (response.body() as AnimeDto).toMainModel()))
                    Type.MANGA -> emit(Resource.Success(data = (response.body() as MangaDto).toMainModel()))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }
    }

    override fun getReviews(id: Int, type: Type): Flow<Resource<List<Review>>> = flow {
        emit(Resource.Loading())

        try {
            val response = when(type) {
                Type.ANIME -> detailsApi.getAnimeReviews(id)
                Type.MANGA -> detailsApi.getMangaReviews(id)
            }
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.toReviews()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }
    }

    override fun getRecommendations(id: Int, type: Type): Flow<Resource<List<Recommendation>>> = flow {
        emit(Resource.Loading())

        try {
            val response = when(type) {
                Type.ANIME -> detailsApi.getAnimeRecommendations(id)
                Type.MANGA -> detailsApi.getMangaRecommendations(id)
            }
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.toRecommendations()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }
    }

    override fun getCharacters(id: Int, type: Type): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())

        try {
            val response = when(type) {
                Type.ANIME -> detailsApi.getAnimeCharacters(id)
                Type.MANGA -> detailsApi.getMangaCharacters(id)
            }
            if (response.isSuccessful) {
                when(type) {
                    Type.ANIME -> emit(Resource.Success(data = (response.body() as AnimeCharactersDto).toCharacters()))
                    Type.MANGA -> emit(Resource.Success(data = (response.body() as MangaCharactersDto).toCharacters()))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }
    }


    override fun getStaff(id: Int): Flow<Resource<List<Staff>>> = flow {
        emit(Resource.Loading())

        try {
            val response = detailsApi.getAnimeStaff(id)
            if (response.isSuccessful) {
                emit(Resource.Success(data = response.body()?.toStaff()))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }
    }
}