package com.example.randomanimegenerator.feature_library.domain.use_case

import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import kotlinx.coroutines.flow.Flow

class GetAllByStatusUseCase(
    private val repo: LibraryRepository
) {
    operator fun invoke(type: String, libraryStatus: String, sortType: LibrarySortType, userUID: String): Flow<List<UserFavoritesEntity>> {
        return when(sortType) {
            LibrarySortType.A_Z -> repo.getAllByStatusAZ(type, libraryStatus, userUID)
            LibrarySortType.Z_A -> repo.getAllByStatusZA(type, libraryStatus, userUID)
            LibrarySortType.NEWEST -> repo.getAllByStatusNewest(type, libraryStatus, userUID)
            LibrarySortType.OLDEST -> repo.getAllByStatusOldest(type, libraryStatus, userUID)
        }
    }
}