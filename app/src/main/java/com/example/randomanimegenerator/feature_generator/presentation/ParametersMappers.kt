package com.example.randomanimegenerator.feature_generator.presentation

fun Amount.toAmountString(): String {
    return when(this) {
        Amount.ONE -> "1"
        Amount.TWENTY_FIVE -> "25"
    }
}

fun Type.toTypeString(): String {
    return when(this) {
        Type.ANIME -> "Anime"
        Type.MANGA -> "Manga"
    }
}

fun String.toType(): Type {
    return when(this) {
        "Anime" -> Type.ANIME
        else -> Type.MANGA
    }
}

fun String.toAmount(): Amount {
    return when(this) {
        "1" -> Amount.ONE
        else -> Amount.TWENTY_FIVE
    }
}