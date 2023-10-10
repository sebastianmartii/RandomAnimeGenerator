package com.example.randomanimegenerator.feature_profile.presentation

import android.content.Intent
import android.content.IntentSender
import com.example.randomanimegenerator.feature_profile.domain.model.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest

interface AuthenticationClient {

    suspend fun signIn(): IntentSender?

    suspend fun signInWithIntent(intent: Intent): SignInResult

    suspend fun signOut()

    fun getSignedInUser(): UserData?

    fun buildSignInRequest(): BeginSignInRequest

    suspend fun signUpWithEmailAndPassword(email: String, password: String)

    suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult
}