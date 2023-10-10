package com.example.randomanimegenerator.feature_profile.presentation

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.feature_profile.domain.model.UserData
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject

class AuthenticationClientImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val oneTapClient: SignInClient
) : AuthenticationClient {
    private val auth = Firebase.auth

    override suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
                buildSignInRequest()
            ).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    override suspend fun signInWithIntent(intent: Intent): SignInResult {
        val credentials = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credentials.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        userName = displayName,
                        profilePictureUrl = photoUrl?.toString()
                    )
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }

    override suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override fun getSignedInUser(): UserData? = auth.currentUser?.run {
        UserData(
            userId = uid,
            userName = displayName,
            profilePictureUrl = photoUrl?.toString()
        )
    }

    override fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.client_server_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    override suspend fun signUpWithEmailAndPassword(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    override suspend fun signInWithEmailAndPassword(email: String, password: String): SignInResult {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            val user = getSignedInUser()
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = userId,
                        userName = userName,
                        profilePictureUrl = profilePictureUrl
                    )
                },
                errorMessage = null,
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(
                data = null,
                errorMessage = e.message,
            )
        }
    }
}