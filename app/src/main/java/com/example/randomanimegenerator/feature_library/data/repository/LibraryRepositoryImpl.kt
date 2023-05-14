package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val mainInfoDao: MainInfoDao,
) : LibraryRepository {

    override fun getAll(type: String): Flow<List<MainInfoEntity>> = mainInfoDao.getAll(type)

    override fun getAllByStatus(type: String, libraryStatus: String): Flow<List<MainInfoEntity>>  = mainInfoDao.getAllByStatus(type, libraryStatus)
}