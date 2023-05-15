package com.example.randomanimegenerator.feature_library.domain.repository

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import kotlinx.coroutines.flow.Flow

interface LibraryRepository {

    fun getAll(type: String, sortType: LibrarySortType): Flow<List<MainInfoEntity>>

    fun getAllByStatus(type: String, libraryStatus: String, sortType: LibrarySortType): Flow<List<MainInfoEntity>>
}