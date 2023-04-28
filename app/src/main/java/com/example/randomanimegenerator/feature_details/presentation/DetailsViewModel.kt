package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_details.domain.use_cases.DetailsUseCases
import com.example.randomanimegenerator.feature_generator.presentation.toType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: DetailsUseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(DetailsState())
    val state = _state.asStateFlow()

    init {
        getMainInfo()
    }

    private fun getMainInfo() {
        viewModelScope.launch {
            useCases.getInfoUseCase(
                id = savedStateHandle.get<Int>("id")!!,
                type = savedStateHandle.get<String>("type")!!.toType()
            ).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            title = result.data?.title ?: "",
                            imageUrl = result.data?.imageUrl ?: "",
                            description = result.data?.synopsis ?: "",
                            additionalInfo = result.data?.statusList ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            title = result.data?.title ?: "",
                            imageUrl = result.data?.imageUrl ?: "",
                            description = result.data?.synopsis ?: "",
                            additionalInfo = result.data?.statusList ?: emptyList(),
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            title = result.data?.title ?: "",
                            imageUrl = result.data?.imageUrl ?: "",
                            description = result.data?.synopsis ?: "",
                            additionalInfo = result.data?.statusList ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
            useCases.getCharactersUseCase(
                id = savedStateHandle.get<Int>("id")!!,
                type = savedStateHandle.get<String>("type")!!.toType()
            ).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            characters = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            characters = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            characters = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
            useCases.getReviewsUseCase(
                id = savedStateHandle.get<Int>("id")!!,
                type = savedStateHandle.get<String>("type")!!.toType()
            ).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            reviews = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            reviews = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            reviews = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
            useCases.getRecommendationsUseCase(
                id = savedStateHandle.get<Int>("id")!!,
                type = savedStateHandle.get<String>("type")!!.toType()
            ).onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            recommendation = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            recommendation = result.data ?: emptyList(),
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            recommendation = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}