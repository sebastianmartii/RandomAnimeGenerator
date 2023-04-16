package com.example.randomanimegenerator.feature_generator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.domain.use_cases.GeneratorUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GeneratorViewModel @Inject constructor(
    private val generatorUseCases: GeneratorUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(GeneratorState())
    val state = _state.asStateFlow()

    fun selectType(type: Type) {
        _state.value = state.value.copy(
            typeSelected = type
        )
    }

    fun selectScore(score: String) {
        _state.value = state.value.copy(
            scoreSelected = score
        )
    }

    fun selectAmount(amount: Amount) {
        _state.value = state.value.copy(
            amountSelected = amount
        )
    }

    fun onGeneratingParamsEdit() {
        _state.value = state.value.copy(
            hasGenerated = false
        )
    }

    fun generate(generatorState: GeneratorState) {
        when(generatorState.amountSelected) {
            Amount.ONE -> {
                generateSingleItem(minScore = generatorState.scoreSelected.toInt(), type = generatorState.typeSelected)
            }
            Amount.TWENTY_FIVE -> {
                generateList(generatorState.scoreSelected.toInt(), type = generatorState.typeSelected)
            }
        }
    }

    private fun generateList(minScore: Int, type: Type) {
        viewModelScope.launch {
            generatorUseCases.generateListUseCase(type = type, minScore = minScore).onEach { result ->
                when(result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            listOfItems = result.data ?: emptyList(),
                            isLoading = false,
                            hasGenerated = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            listOfItems = result.data ?: emptyList(),
                            isLoading = true,
                            hasGenerated = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            listOfItems = result.data ?: emptyList(),
                            isLoading = false,
                            hasGenerated = true,
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private fun generateSingleItem(minScore: Int, type: Type) {
        viewModelScope.launch {
            generatorUseCases.generateSingleItemUseCase(type = type, minScore = minScore)
                .onEach { result ->
                when(result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            listOfItems = result.data ?: emptyList(),
                            isLoading = false,
                            hasGenerated = true
                        )
                        println(result.message)
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            listOfItems = result.data ?: emptyList(),
                            isLoading = true,
                            hasGenerated = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            listOfItems = result.data ?: emptyList(),
                            isLoading = false,
                            hasGenerated = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}
