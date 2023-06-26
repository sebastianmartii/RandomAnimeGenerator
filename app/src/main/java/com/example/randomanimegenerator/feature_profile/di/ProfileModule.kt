package com.example.randomanimegenerator.feature_profile.di

import android.content.Context
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.UserDao
import com.example.randomanimegenerator.core.database.daos.UserFavoritesDao
import com.example.randomanimegenerator.feature_profile.data.repository.ProfileRepositoryImpl
import com.example.randomanimegenerator.feature_profile.domain.repository.ProfileRepository
import com.example.randomanimegenerator.feature_profile.domain.use_cases.GetEntriesCountUseCase
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProfileModule {

    @Provides
    @Singleton
    fun provideGetEntriesCountUseCase(
        repo: ProfileRepository
    ): GetEntriesCountUseCase {
        return GetEntriesCountUseCase(repo)
    }

    @Provides
    @Singleton
    fun provideProfileRepository(
        userFavoritesDao: UserFavoritesDao,
        userDao: UserDao,
        authenticationClient: AuthenticationClient
    ): ProfileRepository {
        return ProfileRepositoryImpl(userFavoritesDao, userDao, authenticationClient)
    }

    @Provides
    @Singleton
    fun provideUserDao(
        db: RandomAnimeGeneratorDb
    ): UserDao {
        return db.userDao
    }

    @Provides
    @Singleton
    fun provideUserFavoritesDao(
        db: RandomAnimeGeneratorDb
    ): UserFavoritesDao {
        return db.userFavoritesDao
    }

    @Provides
    @Singleton
    fun provideOneTapClient(
        @ApplicationContext context: Context
    ): SignInClient {
        return Identity.getSignInClient(context)
    }

    @Provides
    @Singleton
    fun provideAuthenticationClient(
        @ApplicationContext context: Context,
        oneTapClient: SignInClient
    ): AuthenticationClient {
        return AuthenticationClient(context, oneTapClient)
    }
}