package com.example.randomanimegenerator.feature_profile.presentation

sealed interface SignUpEvent {
    data class SignUpWithEmailAndPassword(val email: String, val password: String) : SignUpEvent
    data class OnSignUpResult(val result: SignInResult) : SignUpEvent
    data class SetEmail(val email: String) : SignUpEvent
    data class SetPassword(val password: String) : SignUpEvent
    object ResetState : SignUpEvent
}