package com.example.randomanimegenerator.feature_details.di

import com.example.randomanimegenerator.core.constants.BASE_URL
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.feature_details.data.remote.DetailsApi
import com.example.randomanimegenerator.feature_details.data.repository.DetailsRepositoryImpl
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_details.domain.use_cases.DetailsUseCases
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetCharactersUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetInfoUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetRecommendationsUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetReviewsUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetStaffUseCase
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
object DetailsModule {

    @Provides
    @Singleton
    fun provideDetailsUseCases(
        getRecommendationsUseCase: GetRecommendationsUseCase,
        getReviewsUseCase: GetReviewsUseCase,
        getCharactersUseCase: GetCharactersUseCase,
        getStaffUseCase: GetStaffUseCase,
        getInfoUseCase: GetInfoUseCase
    ): DetailsUseCases {
        return DetailsUseCases(
            getInfoUseCase,
            getCharactersUseCase,
            getRecommendationsUseCase,
            getReviewsUseCase,
            getStaffUseCase
        )
    }

    @Provides
    @Singleton
    fun provideGetStaffUseCase(
        repo: DetailsRepository
    ): GetStaffUseCase {
        return GetStaffUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(
        repo: DetailsRepository
    ): GetCharactersUseCase {
        return GetCharactersUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetReviewsUseCase(
        repo: DetailsRepository
    ): GetReviewsUseCase {
        return GetReviewsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetRecommendationsUseCase(
        repo: DetailsRepository
    ): GetRecommendationsUseCase {
        return GetRecommendationsUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideGetInfoUseCase(
        repo: DetailsRepository
    ): GetInfoUseCase {
        return GetInfoUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(
        detailsApi: DetailsApi,
        database: RandomAnimeGeneratorDb
    ): DetailsRepository {
        return DetailsRepositoryImpl(detailsApi, database)
    }

    @Provides
    @Singleton
    fun provideDetailsApi(): DetailsApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}