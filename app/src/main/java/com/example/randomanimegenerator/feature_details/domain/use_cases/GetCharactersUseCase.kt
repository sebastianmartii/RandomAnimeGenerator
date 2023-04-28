package com.example.randomanimegenerator.feature_details.domain.use_cases

import com.example.randomanimegenerator.feature_details.domain.repository.DetailsRepository
import com.example.randomanimegenerator.feature_generator.presentation.Type

class GetCharactersUseCase(
    private val repository: DetailsRepository
) {
    operator fun invoke(id: Int, type: Type) = repository.getCharacters(id, type)
}