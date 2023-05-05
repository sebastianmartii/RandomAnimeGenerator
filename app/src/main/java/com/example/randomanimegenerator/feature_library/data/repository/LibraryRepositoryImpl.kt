package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val mainInfoDao: MainInfoDao,
) : LibraryRepository {

    override fun getAnime(): Flow<List<MainInfoEntity>> = mainInfoDao.getAll(type = "Anime", isFavorite = true)

    override fun getManga(): Flow<List<MainInfoEntity>> = mainInfoDao.getAll(type = "Manga", isFavorite = true)
}