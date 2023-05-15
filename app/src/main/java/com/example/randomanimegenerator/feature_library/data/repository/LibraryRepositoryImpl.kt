package com.example.randomanimegenerator.feature_library.data.repository

import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import kotlinx.coroutines.flow.Flow

class LibraryRepositoryImpl(
    private val mainInfoDao: MainInfoDao,
) : LibraryRepository {

    override fun getAll(type: String, sortType: LibrarySortType): Flow<List<MainInfoEntity>> {
        return when(sortType) {
            LibrarySortType.A_Z -> mainInfoDao.getAllAZ(type)
            LibrarySortType.Z_A -> mainInfoDao.getAllZA(type)
            LibrarySortType.NEWEST -> mainInfoDao.getAllNewest(type)
            LibrarySortType.OLDEST -> mainInfoDao.getAll(type)
        }
    }

    override fun getAllByStatus(type: String, libraryStatus: String, sortType: LibrarySortType): Flow<List<MainInfoEntity>> {
        return when(sortType) {
            LibrarySortType.A_Z -> mainInfoDao.getAllByStatusAZ(type, libraryStatus)
            LibrarySortType.Z_A -> mainInfoDao.getAllByStatusZA(type, libraryStatus)
            LibrarySortType.NEWEST -> mainInfoDao.getAllByStatusNewest(type, libraryStatus)
            LibrarySortType.OLDEST -> mainInfoDao.getAllByStatus(type, libraryStatus)
        }
    }
}