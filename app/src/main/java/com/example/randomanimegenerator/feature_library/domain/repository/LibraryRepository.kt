package com.example.randomanimegenerator.feature_library.domain.repository

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getAnime(): Flow<List<MainInfoEntity>>

    fun getManga(): Flow<List<MainInfoEntity>>
}