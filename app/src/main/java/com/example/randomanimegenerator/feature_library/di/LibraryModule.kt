package com.example.randomanimegenerator.feature_library.di

import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.feature_library.data.repository.LibraryRepositoryImpl
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
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
    fun provideLibraryRepository(
        database: RandomAnimeGeneratorDb
    ): LibraryRepository {
        return LibraryRepositoryImpl(
            libraryDao = database.libraryDao
        )
    }
}