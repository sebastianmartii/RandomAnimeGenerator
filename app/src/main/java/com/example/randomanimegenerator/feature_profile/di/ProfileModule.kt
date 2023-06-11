package com.example.randomanimegenerator.feature_profile.di

import android.content.Context
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
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
    fun provideMainInfoDao(
        db: RandomAnimeGeneratorDb
    ): MainInfoDao {
        return db.mainInfoDao
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