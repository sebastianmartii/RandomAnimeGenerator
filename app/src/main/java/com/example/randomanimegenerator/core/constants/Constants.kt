package com.example.randomanimegenerator.core.constants

const val BASE_URL = "https://api.jikan.moe"

val listOfTypes = listOf("Anime", "Manga")

val listOfScores = listOf("9", "8", "7", "6", "5")

val listOfAmounts = listOf("1", "25")

val minScoreToPageMapAnime = mapOf(9 to 1, 8 to 25, 7 to 172, 6 to 419, 5 to 584)

val minScoreToPageMapManga = mapOf(9 to 1, 8 to 31, 7 to 120, 6 to 150, 5 to 200)