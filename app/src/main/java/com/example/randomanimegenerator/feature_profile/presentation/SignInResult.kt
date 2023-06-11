package com.example.randomanimegenerator.feature_profile.presentation

import com.example.randomanimegenerator.feature_profile.domain.model.UserData

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?,
    val isLoading: Boolean = true
)
