package com.example.randomanimegenerator.feature_library.domain.repository

import com.example.randomanimegenerator.core.database.entities.LibraryEntity
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getAnime(): Flow<List<LibraryEntity>>

    fun getManga(): Flow<List<LibraryEntity>>
}