package com.example.randomanimegenerator.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationClient: AuthenticationClient
) : ViewModel() {

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when(event) {
            is SignInEvent.OnSignInResult -> {
                _state.update {
                    it.copy(
                        isSignInSuccessful = event.result.data != null,
                        errorMessage = event.result.errorMessage,
                        isLoading = event.result.isLoading
                    )
                }
            }
            is SignInEvent.ResetState -> {
                _state.update {
                    SignInState()
                }
            }
            is SignInEvent.SetEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is SignInEvent.SetPassword -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }
            is SignInEvent.SignInWithEmailAndPassword -> {
                viewModelScope.launch {
                    val signInResult = authenticationClient.signInWithEmailAndPassword(event.email, event.password)
                    onEvent(SignInEvent.OnSignInResult(signInResult))
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                }
            }
        }
    }
}