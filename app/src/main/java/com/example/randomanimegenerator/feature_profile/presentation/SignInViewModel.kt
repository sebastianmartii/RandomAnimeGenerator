package com.example.randomanimegenerator.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.feature_profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationClientImpl: AuthenticationClientImpl,
    private val repo: ProfileRepository
) : ViewModel() {

    private val _channel = Channel<ProfileFeatureUiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnSignInResult -> {
                if (event.result.data != null) {
                    viewModelScope.launch {
                        repo.addUser(event.result.data.userId, event.result.data.userName, event.result.data.profilePictureUrl)
                    }
                } else {
                    viewModelScope.launch {
                        _channel.send(
                            ProfileFeatureUiEvent.ShowSnackBar(
                                "SignIn was not successful, " +
                                        "please go over your credentials and try again."
                            )
                        )
                    }
                }
                _state.update {
                    it.copy(
                        isSignInSuccessful = event.result.data != null,
                        errorMessage = event.result.errorMessage,
                        isLoading = event.result.data != null
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
                    val signInResult =
                        authenticationClientImpl.signInWithEmailAndPassword(event.email, event.password)
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