package com.example.randomanimegenerator.core.di

import android.content.Context
import androidx.room.Room
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainModule {

    @Provides
    @Singleton
    fun provideRandomAnimeGeneratorDatabase(
        @ApplicationContext context: Context
    ): RandomAnimeGeneratorDb {
        return Room.databaseBuilder(
            context = context,
            RandomAnimeGeneratorDb::class.java,
            "random_anime_generator_db"
        ).addMigrations(RandomAnimeGeneratorDb.MIGRATION_1_2).build()
    }
}