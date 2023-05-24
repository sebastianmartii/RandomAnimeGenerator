package com.example.randomanimegenerator.feature_library.domain.use_case

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import kotlinx.coroutines.flow.Flow

class GetAllUseCase(
    private val repo: LibraryRepository
) {
    operator fun invoke(type: String, sortType: LibrarySortType): Flow<List<MainInfoEntity>> {
        return when(sortType) {
            LibrarySortType.A_Z -> repo.getAllAZ(type)
            LibrarySortType.Z_A -> repo.getAllZA(type)
            LibrarySortType.NEWEST -> repo.getAllNewest(type)
            LibrarySortType.OLDEST -> repo.getAllOldest(type)
        }
    }
}