package com.example.randomanimegenerator.feature_details.data.repository

import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.MainModel
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
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
            isFavorite = false,
            libraryStatus = LibraryStatus.PLANNING
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

    override suspend fun addOrRemoveFromFavorites(malId: Int, type: String, isFavorite: Boolean) {}

    override suspend fun updateLibraryStatus(malId: Int, type: String, libraryStatus: String) {}
}