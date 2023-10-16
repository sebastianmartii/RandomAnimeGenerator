package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.data.mappers.toStatus
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_details.domain.use_cases.DetailsUseCases
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toType
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: DetailsUseCases,
    private val repo: DetailsRepository,
    private val authClient: AuthenticationClient,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    private val _type = savedStateHandle.get<String>("type")
    val type: String
        get() = _type!!

    private val _status = repo.getStatus(id!!, _type!!, authClient.getSignedInUser()?.userId ?: "")

    private val _state = MutableStateFlow(DetailsState(type = _type!!.toType()))
    val state = _state.combine(_status) { state, status ->
        state.copy(
            libraryStatus = status.toStatus()
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), DetailsState())

    private val _channel = Channel<UiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    init {
        getIsEntryUserFavorite()
        getScreenContents()
    }

    private fun getIsEntryUserFavorite() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isFavorite = repo.isEntryUserFavorite(id!!, _type!!, authClient.getSignedInUser()?.userId ?: "")
                )
            }
        }
    }

    private fun getScreenContents() {
        viewModelScope.launch {
            val mainInfo = async {
                useCases.getInfoUseCase(
                    id = id!!,
                    type = _type!!.toType()
                )
            }
            val characters = async {
                useCases.getCharactersUseCase(
                    id = id!!,
                    type = _type!!.toType()
                )
            }
            val reviews = async {
                useCases.getReviewsUseCase(
                    id = id!!,
                    type = _type!!.toType()
                )
            }
            mainInfo.await().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                title = result.data?.title ?: "",
                                malId = result.data?.malId ?: 0,
                                imageUrl = result.data?.imageUrl ?: "",
                                largeImageUrl = result.data?.largeImageUrl ?: "",
                                authors = result.data?.authors ?: "",
                                studios = result.data?.studios ?: "",
                                description = result.data?.synopsis ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = false,
                                mainInfoResult = Result.ERROR,
                                entryId = result.data?.entryId ?: 0
                            )
                        }
                        _channel.send(UiEvent.ShowSnackBar(message = result.message ?: "Error"))
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                title = result.data?.title ?: "",
                                malId = result.data?.malId ?: 0,
                                imageUrl = result.data?.imageUrl ?: "",
                                largeImageUrl = result.data?.largeImageUrl ?: "",
                                description = result.data?.synopsis ?: "",
                                authors = result.data?.authors ?: "",
                                studios = result.data?.studios ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = true,
                                mainInfoResult = Result.LOADING,
                                entryId = result.data?.entryId ?: 0
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                title = result.data?.title ?: "",
                                malId = result.data?.malId ?: 0,
                                imageUrl = result.data?.imageUrl ?: "",
                                largeImageUrl = result.data?.largeImageUrl ?: "",
                                description = result.data?.synopsis ?: "",
                                authors = result.data?.authors ?: "",
                                studios = result.data?.studios ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = false,
                                mainInfoResult = Result.SUCCESS,
                                entryId = result.data?.entryId ?: 0
                            )
                        }
                    }
                }
            }
            characters.await().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                characters = result.data ?: emptyList(),
                                isLoading = false,
                                charactersResult = Result.ERROR,
                            )
                        }
                        _channel.send(UiEvent.ShowSnackBar(message = result.message ?: "Error"))
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                characters = result.data ?: emptyList(),
                                isLoading = true,
                                charactersResult = Result.LOADING,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                characters = result.data ?: emptyList(),
                                isLoading = false,
                                charactersResult = Result.SUCCESS,
                            )
                        }
                    }
                }
            }
            reviews.await().collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                reviews = result.data ?: emptyList(),
                                isLoading = false,
                                reviewsResult = Result.ERROR,
                            )
                        }
                        _channel.send(UiEvent.ShowSnackBar(message = result.message ?: "Error"))
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                reviews = result.data ?: emptyList(),
                                isLoading = true,
                                reviewsResult = Result.LOADING,
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                reviews = result.data ?: emptyList(),
                                isLoading = false,
                                reviewsResult = Result.SUCCESS,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: DetailsEvent) {
        when (event) {
            is DetailsEvent.SelectStatus -> {
                _state.update {
                    it.copy(
                        libraryStatus = event.status
                    )
                }
                viewModelScope.launch {
                    repo.updateLibraryStatus(
                        id!!,
                        _type!!,
                        event.status.toStatusString(),
                        authClient.getSignedInUser()?.userId ?: ""
                    )
                }
            }

            is DetailsEvent.AddOrRemoveFromFavorites -> {
                val isLoggedIn = authClient.getSignedInUser() != null
                if (isLoggedIn) {
                    _state.update {
                        it.copy(
                            isFavorite = !event.isFavorite
                        )
                    }
                    viewModelScope.launch {
                        when (event.isFavorite) {
                            true -> {
                                repo.removeFromUserFavorites(
                                    id!!,
                                    _type!!,
                                    authClient.getSignedInUser()?.userId ?: ""
                                )
                                _channel.send(UiEvent.ShowSnackBar("Entry has been deleted from favorites"))
                            }

                            false -> {
                                repo.addToUserFavorites(
                                    id!!,
                                    _type!!,
                                    authClient.getSignedInUser()?.userId ?: "",
                                    LibraryStatus.PLANNING.toStatusString(),
                                    state.value.entryId,
                                    state.value.title,
                                    state.value.imageUrl
                                )
                                _channel.send(UiEvent.ShowSnackBar("Entry has been added to favorites"))
                            }
                        }
                    }
                } else {
                    viewModelScope.launch {
                        _channel.send(UiEvent.ShowSignInSnackBar("Sign In to add to favorites"))
                    }
                }
            }

            is DetailsEvent.GenerateRecommendationsAndStaff -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            getRecommendationsAndStaff = false
                        )
                    }
                    if (event.type == Type.ANIME) {
                        delay(1.seconds)
                        useCases.getStaffUseCase(id!!).collectLatest { result ->
                            when (result) {
                                is Resource.Error -> {
                                    _state.update {
                                        it.copy(
                                            staff = result.data ?: emptyList(),
                                            isLoading = false,
                                            staffResult = Result.ERROR,
                                        )
                                    }
                                    _channel.send(
                                        UiEvent.ShowSnackBar(
                                            message = result.message ?: "Error"
                                        )
                                    )
                                }

                                is Resource.Loading -> {
                                    _state.update {
                                        it.copy(
                                            staff = result.data ?: emptyList(),
                                            isLoading = true,
                                            staffResult = Result.LOADING,
                                        )
                                    }
                                }

                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            staff = result.data ?: emptyList(),
                                            isLoading = false,
                                            staffResult = Result.SUCCESS,
                                        )
                                    }
                                }
                            }
                        }
                    }
                    delay(1.seconds)
                    useCases.getRecommendationsUseCase(id!!, _type!!.toType()).collectLatest { result ->
                        when (result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        recommendation = result.data ?: emptyList(),
                                        isLoading = false,
                                        recommendationsResult = Result.ERROR,
                                    )
                                }
                                _channel.send(
                                    UiEvent.ShowSnackBar(
                                        message = result.message ?: "Error"
                                    )
                                )
                            }

                            is Resource.Loading -> {
                                _state.update {
                                    it.copy(
                                        recommendation = result.data ?: emptyList(),
                                        isLoading = true,
                                        recommendationsResult = Result.LOADING,
                                    )
                                }
                            }

                            is Resource.Success -> {
                                _state.update {
                                    it.copy(
                                        recommendation = result.data ?: emptyList(),
                                        isLoading = false,
                                        recommendationsResult = Result.SUCCESS,
                                    )
                                }
                            }
                        }
                    }
                }
            }
            is DetailsEvent.NavigateToSingleReview -> {
                _state.update {
                    it.copy(
                        spectatedReview = event.review
                    )
                }
            }
            is DetailsEvent.ShowPopUpImage -> {
                _state.update {
                    it.copy(
                        showPopUp = !_state.value.showPopUp,
                        popUpPlaceholder = event.placeholder
                    )
                }
            }
            is DetailsEvent.ExpandSynopsis -> {
                _state.update {
                    it.copy(
                        synopsisExpanded = !_state.value.synopsisExpanded
                    )
                }
            }
            is DetailsEvent.NavigateBackFromSingleReview -> {
                _state.update {
                    it.copy(
                        spectatedReview = null
                    )
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data class ShowSignInSnackBar(val message: String) : UiEvent()
    }
}