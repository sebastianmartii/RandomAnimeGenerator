package com.example.randomanimegenerator.feature_generator.di

import com.example.randomanimegenerator.core.constants.BASE_URL
import com.example.randomanimegenerator.core.databse.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.feature_generator.data.remote.GeneratorApi
import com.example.randomanimegenerator.feature_generator.data.repository.GeneratorRepositoryImpl
import com.example.randomanimegenerator.feature_generator.domain.repository.GeneratorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeneratorModule {

    @Provides
    @Singleton
    fun provideGeneratorRepository(
        generatorApi: GeneratorApi,
        database: RandomAnimeGeneratorDb
    ): GeneratorRepository {
        return GeneratorRepositoryImpl(
            generatorApi = generatorApi,
            generatorDao = database.generatorDao
        )
    }

    @Provides
    @Singleton
    fun provideGeneratorApi(): GeneratorApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

}