package com.example.randomanimegenerator.feature_library.domain.use_case

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import kotlinx.coroutines.flow.Flow

class GetAllByStatusUseCase(
    private val repo: LibraryRepository
) {
    operator fun invoke(type: String, libraryStatus: String, sortType: LibrarySortType): Flow<List<MainInfoEntity>> {
        return when(sortType) {
            LibrarySortType.A_Z -> repo.getAllByStatusAZ(type, libraryStatus)
            LibrarySortType.Z_A -> repo.getAllByStatusZA(type, libraryStatus)
            LibrarySortType.NEWEST -> repo.getAllByStatusNewest(type, libraryStatus)
            LibrarySortType.OLDEST -> repo.getAllByStatusOldest(type, libraryStatus)
        }
    }
}