package com.example.randomanimegenerator.feature_details.data.repository

import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.data.mappers.toCharacter
import com.example.randomanimegenerator.feature_details.data.mappers.toCharactersEntity
import com.example.randomanimegenerator.feature_details.data.mappers.toMainInfoEntity
import com.example.randomanimegenerator.feature_details.data.mappers.toMainModel
import com.example.randomanimegenerator.feature_details.data.mappers.toRecommendation
import com.example.randomanimegenerator.feature_details.data.mappers.toRecommendationsEntity
import com.example.randomanimegenerator.feature_details.data.mappers.toReview
import com.example.randomanimegenerator.feature_details.data.mappers.toReviewsEntity
import com.example.randomanimegenerator.feature_details.data.mappers.toStaff
import com.example.randomanimegenerator.feature_details.data.mappers.toStaffEntity
import com.example.randomanimegenerator.feature_details.data.remote.DetailsApi
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.MainModel
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toType
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.NullPointerException

class DetailsRepositoryImpl(
    private val detailsApi: DetailsApi,
    private val db: RandomAnimeGeneratorDb
) : DetailsRepository {

    override fun getInfo(id: Int, type: Type): Flow<Resource<MainModel>> =
        flow {
            emit(Resource.Loading())

            val dbInfo = db.mainInfoDao.getOne(id, type.toTypeString())
            emit(Resource.Loading(data = dbInfo.toMainModel()))

            val isFavorite = try {
                dbInfo.isFavorite
            } catch (e: NullPointerException) {
                false
            }

            try {
                when (type) {
                    Type.ANIME -> {
                        val response = detailsApi.getAnime(id)
                        db.mainInfoDao.delete(id, type.toTypeString())
                        db.mainInfoDao.insert(response.toMainInfoEntity(type, isFavorite))
                    }

                    Type.MANGA -> {
                        val response = detailsApi.getManga(id)
                        db.mainInfoDao.delete(id, type.toTypeString())
                        db.mainInfoDao.insert(response.toMainInfoEntity(type, isFavorite))
                    }
                }
            } catch (e: IOException) {
                emit(Resource.Error(message = "io error"))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "http error"))
            }

            val newInfo = db.mainInfoDao.getOne(id, type.toTypeString())
            emit(Resource.Success(data = newInfo.toMainModel()))
        }

    override fun getReviews(id: Int, type: Type): Flow<Resource<List<Review>>> = flow {
        emit(Resource.Loading())

        val reviews = db.reviewDao.getReviews(id, type.toTypeString())
        emit(Resource.Loading(data = reviews.map { it.toReview() }))

        try {
            val response = when (type) {
                Type.ANIME -> detailsApi.getAnimeReviews(id)
                Type.MANGA -> detailsApi.getMangaReviews(id)
            }
            db.reviewDao.deleteReviews(id, type.toTypeString())
            db.reviewDao.upsertReviews(response.toReviewsEntity(id, type))
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }

        val newReviews = db.reviewDao.getReviews(id, type.toTypeString())
        emit(Resource.Success(data = newReviews.map { it.toReview() }))
    }

    override fun getRecommendations(id: Int, type: Type): Flow<Resource<List<Recommendation>>> =
        flow {
            emit(Resource.Loading())

            val recommendations = db.recommendationDao.getRecommendations(id, type.toTypeString())
            emit(Resource.Loading(data = recommendations.map { it.toRecommendation() }))

            try {
                val response = when (type) {
                    Type.ANIME -> detailsApi.getAnimeRecommendations(id)
                    Type.MANGA -> detailsApi.getMangaRecommendations(id)
                }
                db.recommendationDao.deleteRecommendations(id, type.toTypeString())
                db.recommendationDao.upsertRecommendations(
                    response.toRecommendationsEntity(
                        id,
                        type
                    )
                )
            } catch (e: IOException) {
                emit(Resource.Error(message = "io error"))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "http error"))
            }

            val newRecommendations =
                db.recommendationDao.getRecommendations(id, type.toTypeString())
            emit(Resource.Success(data = newRecommendations.map { it.toRecommendation() }))
        }

    override fun getCharacters(id: Int, type: Type): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Loading())

        val characters = db.characterDao.getCharacters(id, type.toTypeString())
        emit(Resource.Loading(data = characters.map { it.toCharacter() }))

        try {
            when (type) {
                Type.ANIME -> {
                    val response = detailsApi.getAnimeCharacters(id)
                    db.characterDao.deleteCharacters(id, type.toTypeString())
                    db.characterDao.upsertCharacters(response.toCharactersEntity(id, type))
                }

                Type.MANGA -> {
                    val response = detailsApi.getMangaCharacters(id)
                    db.characterDao.deleteCharacters(id, type.toTypeString())
                    db.characterDao.upsertCharacters(response.toCharactersEntity(id, type))
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }

        val newCharacters = db.characterDao.getCharacters(id, type.toTypeString())
        emit(Resource.Success(data = newCharacters.map { it.toCharacter() }))
    }


    override fun getStaff(id: Int): Flow<Resource<List<Staff>>> = flow {
        emit(Resource.Loading())

        val staff = db.staffDao.getStaff(id)
        emit(Resource.Loading(data = staff.map { it.toStaff() }))

        try {
            val response = detailsApi.getAnimeStaff(id)
            db.staffDao.deleteStaff(id)
            db.staffDao.upsertStaff(response.toStaffEntity(id))
        } catch (e: IOException) {
            emit(Resource.Error(message = "io error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "http error"))
        }

        val newStaff = db.staffDao.getStaff(id)
        emit(Resource.Success(data = newStaff.map { it.toStaff() }))
    }

    override suspend fun addToFavorites(malId: Int, type: String, isFavorite: Boolean) {
        db.mainInfoDao.updateEntry(malId, type)
    }

    override suspend fun deleteFromFavorites(malId: Int, type: String) {
        db.mainInfoDao.delete(malId, type)
        db.characterDao.deleteCharacters(malId, type)
        db.reviewDao.deleteReviews(malId, type)
        db.recommendationDao.deleteRecommendations(malId, type)
        if (type.toType() == Type.ANIME) {
            db.staffDao.deleteStaff(malId)
        }
    }
}