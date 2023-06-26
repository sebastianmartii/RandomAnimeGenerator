package com.example.randomanimegenerator.feature_details.data.repository

import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
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
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class DetailsRepositoryImpl(
    private val detailsApi: DetailsApi,
    private val db: RandomAnimeGeneratorDb,
) : DetailsRepository {

    override fun getStatus(id: Int, type: String, userUID: String): Flow<String> = db.userFavoritesDao.getStatus(type, id, userUID)

    override fun getInfo(id: Int, type: Type): Flow<Resource<MainModel>> =
        flow {
            emit(Resource.Loading())

            val dbInfo = db.mainInfoDao.getOne(id, type.toTypeString())
            emit(Resource.Loading(data = dbInfo.toMainModel()))

            val libraryId = try {
                dbInfo.id
            } catch (e: NullPointerException) {
                null
            }

            try {
                when (type) {
                    Type.ANIME -> {
                        val response = detailsApi.getAnime(id)
                        if (response.isSuccessful) {
                            db.mainInfoDao.delete(id, type.toTypeString())
                            db.mainInfoDao.insert(
                                response.body()!!.toMainInfoEntity(
                                    type,
                                    libraryId,
                                )
                            )
                        }
                    }

                    Type.MANGA -> {
                        val response = detailsApi.getManga(id)
                        if (response.isSuccessful) {
                            db.mainInfoDao.delete(id, type.toTypeString())
                            db.mainInfoDao.insert(
                                response.body()!!.toMainInfoEntity(
                                    type,
                                    libraryId,
                                )
                            )
                        }
                    }
                }
            } catch (e: IOException) {
                emit(Resource.Error(message = "$e"))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "$e"))
            } catch (e: NullPointerException) {
                emit(Resource.Error(message = "$e"))
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
            if (response.isSuccessful) {
                db.reviewDao.deleteReviews(id, type.toTypeString())
                db.reviewDao.upsertReviews(response.body()!!.toReviewsEntity(id, type))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "$e"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "$e"))
        } catch (e: NullPointerException) {
            emit(Resource.Error(message = "$e"))
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
                if (response.isSuccessful) {
                    db.recommendationDao.deleteRecommendations(id, type.toTypeString())
                    db.recommendationDao.upsertRecommendations(
                        response.body()!!.toRecommendationsEntity(
                            id,
                            type
                        )
                    )
                }
            } catch (e: IOException) {
                emit(Resource.Error(message = "$e"))
            } catch (e: HttpException) {
                emit(Resource.Error(message = "$e"))
            } catch (e: NullPointerException) {
                emit(Resource.Error(message = "$e"))
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
                    if (response.isSuccessful) {
                        db.characterDao.deleteCharacters(id, type.toTypeString())
                        db.characterDao.upsertCharacters(response.body()!!.toCharactersEntity(id, type))
                    }
                }

                Type.MANGA -> {
                    val response = detailsApi.getMangaCharacters(id)
                    if (response.isSuccessful) {
                        db.characterDao.deleteCharacters(id, type.toTypeString())
                        db.characterDao.upsertCharacters(response.body()!!.toCharactersEntity(id, type))
                    }
                }
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "$e"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "$e"))
        } catch (e: NullPointerException) {
            emit(Resource.Error(message = "$e"))
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
            if (response.isSuccessful) {
                db.staffDao.deleteStaff(id)
                db.staffDao.upsertStaff(response.body()!!.toStaffEntity(id))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "$e"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = "$e"))
        } catch (e: NullPointerException) {
            emit(Resource.Error(message = "$e"))
        }

        val newStaff = db.staffDao.getStaff(id)
        emit(Resource.Success(data = newStaff.map { it.toStaff() }))
    }

    override suspend fun addToUserFavorites(
        malId: Int,
        type: String,
        userUID: String,
        status: String,
        entryId: Int,
        title: String,
        imageUrl: String
    ) {
        db.userFavoritesDao.addToUserFavorites(UserFavoritesEntity(
            entryMalID = malId,
            entryType = type,
            userUID = userUID,
            entryStatus = status,
            title = title,
            imageUrl = imageUrl
        ))
    }

    override suspend fun removeFromUserFavorites(malId: Int, type: String, userUID: String) {
        db.userFavoritesDao.deleteFromUserFavorites(malId, type, userUID)
    }

    override suspend fun isEntryUserFavorite(malId: Int, type: String, userUID: String): Boolean {
        val userFavorites = db.userFavoritesDao.getUserFavorites(type, userUID)
        return userFavorites.contains(malId)
    }

    override suspend fun updateLibraryStatus(malId: Int, type: String, libraryStatus: String, userUID: String) {
        db.userFavoritesDao.updateEntryStatus(malId, type, libraryStatus, userUID)
    }
}