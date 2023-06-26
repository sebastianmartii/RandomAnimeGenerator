package com.example.randomanimegenerator.feature_library.di

import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.feature_library.data.repository.LibraryRepositoryImpl
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import com.example.randomanimegenerator.feature_library.domain.use_case.GetAllByStatusUseCase
import com.example.randomanimegenerator.feature_library.domain.use_case.GetAllUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object LibraryModule {

    @Provides
    @Singleton
    fun provideGetAllUseCase(
        repository: LibraryRepository
    ): GetAllUseCase {
        return GetAllUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllByStatusUseCase(
        repository: LibraryRepository
    ): GetAllByStatusUseCase {
        return GetAllByStatusUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLibraryRepository(
        database: RandomAnimeGeneratorDb
    ): LibraryRepository {
        return LibraryRepositoryImpl(
            userFavoritesDao = database.userFavoritesDao,
        )
    }
}