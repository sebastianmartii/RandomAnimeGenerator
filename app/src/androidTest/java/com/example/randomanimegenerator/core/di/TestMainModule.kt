package com.example.randomanimegenerator.core.di

import android.content.Context
import androidx.room.Room
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestMainModule {

    @Provides
    @Singleton
    @Named("test_db")
    fun provideInMemoryDb(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
            context,
            RandomAnimeGeneratorDb::class.java
        ).allowMainThreadQueries().addMigrations(RandomAnimeGeneratorDb.MIGRATION_1_2).build()
}