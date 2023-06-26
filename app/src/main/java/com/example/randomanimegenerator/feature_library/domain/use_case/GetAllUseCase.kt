package com.example.randomanimegenerator.feature_library.domain.use_case

import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import kotlinx.coroutines.flow.Flow

class GetAllUseCase(
    private val repo: LibraryRepository
) {
    operator fun invoke(type: String, sortType: LibrarySortType, userUID: String): Flow<List<UserFavoritesEntity>> {
        return when(sortType) {
            LibrarySortType.A_Z -> repo.getAllAZ(type, userUID)
            LibrarySortType.Z_A -> repo.getAllZA(type, userUID)
            LibrarySortType.NEWEST -> repo.getAllNewest(type, userUID)
            LibrarySortType.OLDEST -> repo.getAllOldest(type, userUID)
        }
    }
}