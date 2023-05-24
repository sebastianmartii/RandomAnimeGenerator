package com.example.randomanimegenerator.feature_library.domain.repository

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getAllAZ(type: String): Flow<List<MainInfoEntity>>
    fun getAllZA(type: String): Flow<List<MainInfoEntity>>
    fun getAllNewest(type: String): Flow<List<MainInfoEntity>>
    fun getAllOldest(type: String): Flow<List<MainInfoEntity>>
    fun getAllByStatusAZ(type: String, status: String): Flow<List<MainInfoEntity>>
    fun getAllByStatusZA(type: String, status: String): Flow<List<MainInfoEntity>>
    fun getAllByStatusNewest(type: String, status: String): Flow<List<MainInfoEntity>>
    fun getAllByStatusOldest(type: String, status: String): Flow<List<MainInfoEntity>>
}