package com.example.randomanimegenerator.feature_details.data.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.MainModel
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_generator.presentation.Type
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDetailsRepository : DetailsRepository {

    override fun getInfo(id: Int, type: Type): Flow<Resource<MainModel>> = flow {
        emit(Resource.Success(data = MainModel(
            authors = "",
            chapters = 0,
            source = "",
            episodes = 0,
            studios = "",
            malId = id,
            title = "",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 0.0,
            genres = "",
            themes = "",
            demographic = "",
            isLoading = false,
            entryId = 0
        )))
    }

    override fun getReviews(id: Int, type: Type): Flow<Resource<List<Review>>> = flow {
        emit(Resource.Success(data = listOf(
            Review("$type", id, "0"),
            Review("$type", id, "1"),
            Review("$type", id, "2"),
            Review("$type", id, "3"),
            Review("$type", id, "4"),
        )))
    }

    override fun getRecommendations(id: Int, type: Type): Flow<Resource<List<Recommendation>>> = flow {
        emit(Resource.Success(data = listOf(
            Recommendation("0", "0", id),
            Recommendation("1", "1", id),
            Recommendation("2", "2", id),
            Recommendation("3", "3", id),
            Recommendation("4", "4", id),
        )))
    }

    override fun getCharacters(id: Int, type: Type): Flow<Resource<List<Character>>> = flow {
        emit(Resource.Success(data = listOf(
            Character("0", "$id", "0"),
            Character("1", "$id", "1"),
            Character("2", "$id", "2"),
            Character("3", "$id", "3"),
            Character("4", "$id", "4"),
        )))
    }

    override fun getStaff(id: Int): Flow<Resource<List<Staff>>> = flow {
        emit(Resource.Success(data = listOf(
            Staff("0", "0", "$id"),
            Staff("1", "1", "$id"),
            Staff("2", "2", "$id"),
            Staff("3", "3", "$id"),
            Staff("4", "4", "$id"),
        )))
    }

    private var status: String = "planning"
    private var isFavorite: Boolean = false

    override fun getStatus(id: Int, type: String, userUID: String): Flow<String> = flow {
        emit(status)
    }

    override suspend fun removeFromUserFavorites(malId: Int, type: String, userUID: String) {
        isFavorite = false
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
        isFavorite = true
    }

    override suspend fun isEntryUserFavorite(malId: Int, type: String, userUID: String): Boolean = isFavorite

    override suspend fun updateLibraryStatus(
        malId: Int,
        type: String,
        libraryStatus: String,
        userUID: String
    ) {
        status = libraryStatus
    }
}