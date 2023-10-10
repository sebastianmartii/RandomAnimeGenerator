package com.example.randomanimegenerator.feature_details.presentation

import android.content.Intent
import android.content.IntentSender
import com.example.randomanimegenerator.feature_profile.domain.model.UserData
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import com.example.randomanimegenerator.feature_profile.presentation.SignInResult
import com.google.android.gms.auth.api.identity.BeginSignInRequest

class AuthenticationClientTest : AuthenticationClient {

    override suspend fun signIn(): IntentSender? {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithIntent(intent: Intent): SignInResult {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }

    override fun getSignedInUser(): UserData? {
        return UserData(
            userId = "",
            profilePictureUrl = "",
            userName = ""
        )
    }

    override fun buildSignInRequest(): BeginSignInRequest {
        TODO("Not yet implemented")
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
        TODO("Not yet implemented")
    }
}