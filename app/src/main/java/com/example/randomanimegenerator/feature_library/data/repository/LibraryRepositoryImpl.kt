package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val mainInfoDao: MainInfoDao,
) : LibraryRepository {

    override fun getAllAZ(type: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllAZ(type)

    override fun getAllZA(type: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllZA(type)

    override fun getAllNewest(type: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllNewest(type)

    override fun getAllOldest(type: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAll(type)

    override fun getAllByStatusAZ(type: String, status: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllByStatusAZ(type, status)

    override fun getAllByStatusZA(type: String, status: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllByStatusZA(type, status)

    override fun getAllByStatusNewest(type: String, status: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllByStatusNewest(type, status)

    override fun getAllByStatusOldest(type: String, status: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAllByStatus(type, status)
}