package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeLibraryRepository : LibraryRepository {

    private val mainInfo = listOf(
        MainInfoEntity(
            id = 1,
            malId = 1,
            title = "title 1",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        ),
        MainInfoEntity(
            id = 4,
            malId = 4,
            title = "title 4",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        ),
        MainInfoEntity(
            id = 5,
            malId = 5,
            title = "title 5",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        ),
        MainInfoEntity(
            id = 6,
            malId = 6,
            title = "title 6",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        ),
        MainInfoEntity(
            id = 3,
            malId = 3,
            title = "title 3",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        ),
        MainInfoEntity(
            id = 2,
            malId = 2,
            title = "title 2",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        ),
        MainInfoEntity(
            id = 7,
            malId = 7,
            title = "title 7",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        ),
        MainInfoEntity(
            id = 8,
            malId = 8,
            title = "title 8",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        ),
        MainInfoEntity(
            id = 9,
            malId = 9,
            title = "title 9",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        )
    )

    override fun getAllAZ(type: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.sortedBy { it.title })
    }

    override fun getAllZA(type: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.sortedByDescending { it.title })
    }

    override fun getAllNewest(type: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.sortedBy { it.id })
    }

    override fun getAllOldest(type: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.sortedByDescending { it.id })
    }

    override fun getAllByStatusAZ(type: String, status: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.filter { it.libraryStatus == status }.sortedBy { it.title })
    }

    override fun getAllByStatusZA(type: String, status: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.filter { it.libraryStatus == status }.sortedByDescending { it.title })
    }

    override fun getAllByStatusNewest(type: String, status: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.filter { it.libraryStatus == status }.sortedBy { it.id })
    }

    override fun getAllByStatusOldest(type: String, status: String): Flow<List<MainInfoEntity>> = flow {
        emit(mainInfo.filter { it.libraryStatus == status }.sortedByDescending { it.id })
    }
}