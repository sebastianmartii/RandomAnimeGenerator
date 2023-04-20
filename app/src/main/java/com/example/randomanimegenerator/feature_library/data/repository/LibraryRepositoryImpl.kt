package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.daos.LibraryDao
import com.example.randomanimegenerator.core.database.entities.LibraryEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val libraryDao: LibraryDao,
) : LibraryRepository {

    override fun getAnime(): Flow<List<LibraryEntity>> = libraryDao.getAll(type = "Anime")

    override fun getManga(): Flow<List<LibraryEntity>> = libraryDao.getAll(type = "Manga")
}