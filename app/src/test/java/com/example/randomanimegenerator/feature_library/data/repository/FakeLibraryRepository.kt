package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLibraryRepository : LibraryRepository {

    private val userFavoritesList = listOf(
        UserFavoritesEntity(
            id = 1,
            title = "title a",
            imageUrl = "",
            entryMalID = 1,
            entryStatus = "planning",
            entryType = "",
            userUID = ""
        ),UserFavoritesEntity(
            id = 2,
            title = "title b",
            imageUrl = "",
            entryMalID = 2,
            entryStatus = "planning",
            entryType = "",
            userUID = ""
        ),UserFavoritesEntity(
            id = 3,
            title = "title c",
            imageUrl = "",
            entryMalID = 3,
            entryStatus = "finished",
            entryType = "",
            userUID = ""
        ),UserFavoritesEntity(
            id = 4,
            title = "title d",
            imageUrl = "",
            entryMalID = 4,
            entryStatus = "finished",
            entryType = "",
            userUID = ""
        ),UserFavoritesEntity(
            id = 5,
            title = "title e",
            imageUrl = "",
            entryMalID = 5,
            entryStatus = "planning",
            entryType = "",
            userUID = ""
        ),UserFavoritesEntity(
            id = 6,
            title = "title f",
            imageUrl = "",
            entryMalID = 6,
            entryStatus = "planning",
            entryType = "",
            userUID = ""
        ),
    )


    override fun getAllAZ(type: String, userUID: String): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.sortedBy { it.title })
    }

    override fun getAllZA(type: String, userUID: String): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.sortedByDescending { it.title })
    }

    override fun getAllNewest(type: String, userUID: String): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.sortedBy { it.id })
    }

    override fun getAllOldest(type: String, userUID: String): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.sortedByDescending { it.id })
    }

    override fun getAllByStatusAZ(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.filter { it.entryStatus == status }.sortedBy { it.title })
    }

    override fun getAllByStatusZA(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.filter { it.entryStatus == status }.sortedByDescending { it.title })
    }

    override fun getAllByStatusNewest(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.filter { it.entryStatus == status }.sortedBy { it.id })
    }

    override fun getAllByStatusOldest(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> = flow {
        emit(userFavoritesList.filter { it.entryStatus == status }.sortedByDescending { it.id })
    }
}