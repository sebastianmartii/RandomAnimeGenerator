package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_details.domain.use_cases.DetailsUseCases
import com.example.randomanimegenerator.feature_generator.presentation.toType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: DetailsUseCases,
    private val repo: DetailsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    private val type = savedStateHandle.get<String>("type")

    private val _state = MutableStateFlow(DetailsState(type = type!!.toType()))
    val state = _state.asStateFlow()

    private val _channel = Channel<UiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    init {
        getScreenContents()
    }

    private fun getScreenContents() {
        viewModelScope.launch {
            val mainInfo = async {
                useCases.getInfoUseCase(
                    id = id!!,
                    type = type!!.toType()
                )
            }
            val characters = async {
                useCases.getCharactersUseCase(
                    id = id!!,
                    type = type!!.toType()
                )
            }
            val reviews = async {
                useCases.getReviewsUseCase(
                    id = id!!,
                    type = type!!.toType()
                )
            }
            mainInfo.await().onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                title = result.data?.title ?: "",
                                malId = result.data?.malId ?: 0,
                                imageUrl = result.data?.imageUrl ?: "",
                                authors = result.data?.authors ?: "",
                                studios = result.data?.studios ?: "",
                                description = result.data?.synopsis ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = false,
                                isFavorite = result.data?.isFavorite ?: false
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
                                description = result.data?.synopsis ?: "",
                                authors = result.data?.authors ?: "",
                                studios = result.data?.studios ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = true,
                                isFavorite = result.data?.isFavorite ?: false
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                title = result.data?.title ?: "",
                                malId = result.data?.malId ?: 0,
                                imageUrl = result.data?.imageUrl ?: "",
                                description = result.data?.synopsis ?: "",
                                authors = result.data?.authors ?: "",
                                studios = result.data?.studios ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = false,
                                isFavorite = result.data?.isFavorite ?: false
                            )
                        }
                    }
                }
            }.launchIn(this)
            characters.await().onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                characters = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        _channel.send(UiEvent.ShowSnackBar(message = result.message ?: "Error"))
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                characters = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                characters = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }.launchIn(this)
            reviews.await().onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.update {
                            it.copy(
                                reviews = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                        _channel.send(UiEvent.ShowSnackBar(message = result.message ?: "Error"))
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                reviews = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }

                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                reviews = result.data ?: emptyList(),
                                isLoading = false
                            )
                        }
                    }
                }
            }.launchIn(this)
        }
    }

    fun onEvent(event: DetailsEvent) {
        when(event) {
            is DetailsEvent.SelectStatus -> {
                _state.update {
                    it.copy(
                        libraryStatus = event.status
                    )
                }
                viewModelScope.launch {
                    repo.updateLibraryStatus(id!!, type!!, event.status.toStatusString())
                }
            }
            is DetailsEvent.AddOrRemoveFromFavorites -> {
                _state.update {
                    it.copy(
                        isFavorite = !event.isFavorite
                    )
                }
                viewModelScope.launch {
                    when(event.isFavorite) {
                        true -> {
                            repo.addOrRemoveFromFavorites(id!!, type!!, false)
                            _channel.send(UiEvent.ShowSnackBar("Entry has been deleted from favorites"))
                        }
                        false -> {
                            repo.addOrRemoveFromFavorites(id!!, type!!, true)
                            _channel.send(UiEvent.ShowSnackBar("Entry has been added to favorites"))
                        }
                    }
                }
            }
            is DetailsEvent.GenerateRecommendations -> {
                viewModelScope.launch {
                    val recommendations = async {
                        useCases.getRecommendationsUseCase(id!!, type!!.toType()).onEach { result ->
                            when (result) {
                                is Resource.Error -> {
                                    _state.update {
                                        it.copy(
                                            recommendation = result.data ?: emptyList(),
                                            isLoading = false,
                                            getRecommendations = false
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
                                            getRecommendations = false
                                        )
                                    }
                                }

                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            recommendation = result.data ?: emptyList(),
                                            isLoading = false,
                                            getRecommendations = false
                                        )
                                    }
                                }
                            }
                        }.launchIn(this)
                    }
                    recommendations.await()
                }
            }
            is DetailsEvent.GenerateStaff -> {
                viewModelScope.launch {
                    val staff = async {
                        useCases.getStaffUseCase(id!!).onEach { result ->
                            when (result) {
                                is Resource.Error -> {
                                    _state.update {
                                        it.copy(
                                            staff = result.data ?: emptyList(),
                                            isLoading = false,
                                            getStaff = false
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
                                            getStaff = false
                                        )
                                    }
                                }

                                is Resource.Success -> {
                                    _state.update {
                                        it.copy(
                                            staff = result.data ?: emptyList(),
                                            isLoading = false,
                                            getStaff = false
                                        )
                                    }
                                }
                            }
                        }.launchIn(this)
                    }
                    staff.await()
                }
            }
            is DetailsEvent.NavigateBack -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.NavigateBack)
                }
            }
            is DetailsEvent.NavigateToDestination -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.NavigateToDestination("${event.destination}/$id/$type"))
                }
            }
            is DetailsEvent.NavigateToSingleReview -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.NavigateToDestination("${event.destination}/${event.author}/${event.score}/${event.review}"))
                }
            }
            is DetailsEvent.NavigateToRecommendation -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.NavigateToDestination("${event.destination}/${event.malId}/$type"))
                }
            }
        }
    }


    sealed class UiEvent {
        data class ShowSnackBar(val message: String) : UiEvent()
        data class NavigateToDestination(val destinationRoute: String) : UiEvent()
        object NavigateBack : UiEvent()
    }
}