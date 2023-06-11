package com.example.randomanimegenerator.feature_profile.presentation

sealed interface SignInEvent {
    object ResetState : SignInEvent
    data class SignInWithEmailAndPassword(val email: String, val password: String) : SignInEvent
    data class OnSignInResult(val result: SignInResult) : SignInEvent
    data class SetEmail(val email: String) : SignInEvent
    data class SetPassword(val password: String) : SignInEvent
}