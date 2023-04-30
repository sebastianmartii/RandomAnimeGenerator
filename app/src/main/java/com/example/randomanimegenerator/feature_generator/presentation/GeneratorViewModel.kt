package com.example.randomanimegenerator.feature_generator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.domain.use_case.GenerateContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratorViewModel @Inject constructor(
    private val generateContentUseCase: GenerateContentUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(GeneratorState())
    val state = _state.asStateFlow()

    fun onEvent(event: GeneratorEvent) {
        when(event) {
            is GeneratorEvent.EditGeneratorParams -> {
                _state.value = state.value.copy(
                    editGeneratingParams = true
                )
            }
            is GeneratorEvent.Generate -> {
                viewModelScope.launch {
                    generateContentUseCase(type = event.state.typeSelected, minScore = event.state.scoreSelected.toInt()).onEach { result ->
                        when(result) {
                            is Resource.Error -> {
                                _state.value = state.value.copy(
                                    listOfItems = if (event.state.amountSelected == Amount.ONE) {
                                        result.data?.shuffled()?.subList(0,1) ?: emptyList()
                                    } else {
                                        result.data ?: emptyList()
                                    },
                                    isLoading = false,
                                    editGeneratingParams = false
                                )
                            }
                            is Resource.Loading -> {
                                _state.value = state.value.copy(
                                    listOfItems = if (event.state.amountSelected == Amount.ONE) {
                                        result.data?.shuffled()?.subList(0,1) ?: emptyList()
                                    } else {
                                        result.data ?: emptyList()
                                    },
                                    isLoading = true,
                                    editGeneratingParams = false
                                )
                            }
                            is Resource.Success -> {
                                _state.value = state.value.copy(
                                    listOfItems = if (event.state.amountSelected == Amount.ONE) {
                                        result.data?.shuffled()?.subList(0,1) ?: emptyList()
                                    } else {
                                        result.data ?: emptyList()
                                    },
                                    isLoading = false,
                                    editGeneratingParams = false
                                )
                            }
                        }
                    }.launchIn(this)
                }
            }
            is GeneratorEvent.SetAmount -> {
                _state.value = state.value.copy(
                    amountSelected = event.amount
                )
            }
            is GeneratorEvent.SetScore -> {
                _state.value = state.value.copy(
                    scoreSelected = event.score
                )
            }
            is GeneratorEvent.SetType -> {
                _state.value = state.value.copy(
                    typeSelected = event.type
                )
            }
        }
    }
}
