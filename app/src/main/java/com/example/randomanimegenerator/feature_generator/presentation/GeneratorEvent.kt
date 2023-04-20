package com.example.randomanimegenerator.feature_generator.presentation

import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

sealed interface GeneratorEvent {
    object EditGeneratorParams: GeneratorEvent
    data class Generate(val state: GeneratorState): GeneratorEvent
    data class SetType(val type: Type): GeneratorEvent
    data class SetAmount(val amount: Amount): GeneratorEvent
    data class SetScore(val score: String): GeneratorEvent
    data class Add(val type: Type, var content: GeneratorModel): GeneratorEvent
}