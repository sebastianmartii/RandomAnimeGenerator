package com.example.randomanimegenerator.feature_library.domain.repository

import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getAllAZ(type: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllZA(type: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllNewest(type: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllOldest(type: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllByStatusAZ(type: String, status: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllByStatusZA(type: String, status: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllByStatusNewest(type: String, status: String, userUID: String): Flow<List<UserFavoritesEntity>>
    fun getAllByStatusOldest(type: String, status: String, userUID: String): Flow<List<UserFavoritesEntity>>
}