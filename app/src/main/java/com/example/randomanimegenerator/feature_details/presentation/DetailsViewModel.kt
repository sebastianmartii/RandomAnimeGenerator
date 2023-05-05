package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_details.domain.use_cases.DetailsUseCases
import com.example.randomanimegenerator.feature_generator.presentation.toType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: DetailsUseCases,
    private val repo: DetailsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id = savedStateHandle.get<Int>("id")
    private val type = savedStateHandle.get<String>("type")

    private val _state = MutableStateFlow(DetailsState(type = type!!.toType()))
    val state = _state.asStateFlow()

    init {
        getMainInfo()
    }

    private fun getMainInfo() {
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
                                description = result.data?.synopsis ?: "",
                                additionalInfo = result.data?.statusList ?: emptyList(),
                                isLoading = false,
                                isFavorite = result.data?.isFavorite ?: false
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update {
                            it.copy(
                                title = result.data?.title ?: "",
                                malId = result.data?.malId ?: 0,
                                imageUrl = result.data?.imageUrl ?: "",
                                description = result.data?.synopsis ?: "",
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
            is DetailsEvent.AddOrRemoveFromFavorites -> {
                _state.update {
                    it.copy(
                        isFavorite = !event.isFavorite
                    )
                }
                viewModelScope.launch {
                    when(event.isFavorite) {
                        true -> {
                            repo.deleteFromFavorites(id!!, type!!)
                        }
                        false -> {
                            repo.addToFavorites(id!!, type!!, true)
                        }
                    }
                }
            }
            is DetailsEvent.GenerateRecommendations -> {
                viewModelScope.launch {
                    useCases.getRecommendationsUseCase(id!!, type!!.toType()).onEach { result ->
                        when(result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        recommendation = result.data ?: emptyList(),
                                        isLoading = false,
                                        getRecommendations = false
                                    )
                                }
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
            }
            is DetailsEvent.GenerateStaff -> {
                viewModelScope.launch {
                    useCases.getStaffUseCase(id!!).onEach { result ->
                        when(result) {
                            is Resource.Error -> {
                                _state.update {
                                    it.copy(
                                        staff = result.data ?: emptyList(),
                                        isLoading = false,
                                        getStaff = false
                                    )
                                }
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
            }
        }
    }
}