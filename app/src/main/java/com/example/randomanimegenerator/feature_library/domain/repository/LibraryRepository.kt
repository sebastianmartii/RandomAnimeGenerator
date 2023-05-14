package com.example.randomanimegenerator.feature_library.domain.repository

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getAll(type: String): Flow<List<MainInfoEntity>>

    fun getAllByStatus(type: String, libraryStatus: String): Flow<List<MainInfoEntity>>
}