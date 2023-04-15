package com.example.randomanimegenerator.feature_generator.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.util.Resource
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel
import com.example.randomanimegenerator.feature_generator.domain.repository.GeneratorRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GeneratorViewModel @Inject constructor(
    private val generatorRepository: GeneratorRepository
) : ViewModel() {

    private val _state = MutableStateFlow(GeneratorState())
    val state = _state.asStateFlow()

    fun selectType(type: String) {
        _state.value = state.value.copy(
            typeSelected = type
        )
    }

    fun selectScore(score: String) {
        _state.value = state.value.copy(
            scoreSelected = score
        )
    }

    fun selectAmount(amount: String) {
        _state.value = state.value.copy(
            amountSelected = amount
        )
    }

    fun generate(generatorState: GeneratorState) {
        when(generatorState.amountSelected) {
            "1" -> {
                generateAnime(generatorState.scoreSelected.toInt())
            }
            "25" -> {
                generateAnimeList(generatorState.scoreSelected.toInt())
            }
        }
    }

    private fun generateAnimeList(minScore: Int) {
        viewModelScope.launch {
            generatorRepository.generateAnimeList(page = Random.nextInt(172), minScore = minScore).onEach { result ->
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

    private fun generateAnime(minScore: Int) {
        viewModelScope.launch {
            val randomMalId = generateAnimeIds(minScore = minScore)
            generatorRepository.generateAnime(randomMalId.random()).onEach { result ->
                when(result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            item = result.data ?: GeneratorModel(),
                            isLoading = false,
                            hasGenerated = false
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(
                            item = result.data ?: GeneratorModel(),
                            isLoading = true,
                            hasGenerated = true
                        )
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            item = result.data ?: GeneratorModel(),
                            isLoading = false,
                            hasGenerated = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }

    private suspend fun generateAnimeIds(minScore: Int): List<Int> {
        val page = Random.nextInt(172)
        return generatorRepository.generateAnimeIds(page = page, minScore = minScore).toIdList()
    }
}
