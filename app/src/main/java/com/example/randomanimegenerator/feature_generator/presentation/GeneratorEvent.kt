package com.example.randomanimegenerator.feature_generator.presentation

sealed interface GeneratorEvent {
    object EditGeneratorParams : GeneratorEvent
    data class Generate(val state: GeneratorState) : GeneratorEvent
    data class SetType(val type: Type) : GeneratorEvent
    data class SetAmount(val amount: Amount) : GeneratorEvent
    data class SetScore(val score: String) : GeneratorEvent
}