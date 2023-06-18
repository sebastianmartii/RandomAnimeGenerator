package com.example.randomanimegenerator.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.isEmailValid
import com.example.randomanimegenerator.core.util.isPasswordValid
import com.example.randomanimegenerator.feature_profile.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationClient: AuthenticationClient,
    private val repo: ProfileRepository
) : ViewModel() {

    private val _channel = Channel<ProfileFeatureUiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    private val _state = MutableStateFlow(SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.SetEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is SignUpEvent.SetPassword -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is SignUpEvent.SignUpWithEmailAndPassword -> {
                viewModelScope.launch {
                    if (!event.email.isEmailValid() || !event.password.isPasswordValid()) {
                        _channel.send(ProfileFeatureUiEvent.ShowSnackBar("Provided email or/and password are invalid"))
                        return@launch
                    }
                    authenticationClient.signUpWithEmailAndPassword(event.email, event.password)
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    delay(1000L)
                    val signUpResult =
                        authenticationClient.signInWithEmailAndPassword(event.email, event.password)
                    onEvent(SignUpEvent.OnSignUpResult(signUpResult))
                }
            }

            is SignUpEvent.OnSignUpResult -> {
                if (event.result.data != null) {
                    viewModelScope.launch {
                        repo.addUser(event.result.data.userId, event.result.data.userName, event.result.data.profilePictureUrl)
                    }
                } else {
                    viewModelScope.launch {
                        _channel.send(
                            ProfileFeatureUiEvent.ShowSnackBar(
                                "SignUp was not successful, " +
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

            SignUpEvent.ResetState -> {
                _state.update { SignInState() }
            }
        }
    }
}