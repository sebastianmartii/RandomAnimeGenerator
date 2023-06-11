package com.example.randomanimegenerator.feature_profile.presentation

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isSignInSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
