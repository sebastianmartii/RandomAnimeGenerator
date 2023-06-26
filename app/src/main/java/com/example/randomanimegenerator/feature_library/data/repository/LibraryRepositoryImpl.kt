package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.daos.UserFavoritesDao
import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val userFavoritesDao: UserFavoritesDao,
) : LibraryRepository {

    override fun getAllAZ(type: String, userUID: String): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllAZ(type, userUID)

    override fun getAllZA(type: String, userUID: String): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllZA(type, userUID)

    override fun getAllNewest(type: String, userUID: String): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllNewest(type, userUID)

    override fun getAllOldest(type: String, userUID: String): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllOldest(type, userUID)

    override fun getAllByStatusAZ(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllByStatusAZ(type, status, userUID)

    override fun getAllByStatusZA(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> = userFavoritesDao.getAllByStatusZA(type, status, userUID)

    override fun getAllByStatusNewest(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllByStatusNewest(type, status, userUID)

    override fun getAllByStatusOldest(
        type: String,
        status: String,
        userUID: String
    ): Flow<List<UserFavoritesEntity>> =
        userFavoritesDao.getAllByStatusOldest(type, status, userUID)
}