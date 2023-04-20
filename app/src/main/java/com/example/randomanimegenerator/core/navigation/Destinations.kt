package com.example.randomanimegenerator.core.navigation

sealed class Destinations(val route: String) {
    object Generator: Destinations(route = "generator")
    object AnimeLibrary: Destinations(route = "anime")
    object MangaLibrary: Destinations(route = "manga")
}