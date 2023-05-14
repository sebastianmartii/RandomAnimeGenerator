package com.example.randomanimegenerator.core.navigation

sealed class Destinations(val route: String) {

    object BottomBarGraph: Destinations(route = "bottom")
    object Generator: Destinations(route = "generator")
    object AnimeLibrary: Destinations(route = "anime/{type}")
    object MangaLibrary: Destinations(route = "manga/{type}")
    object DetailsNavGraph: Destinations(route = "details")
    object Details: Destinations(route = "details/{id}/{type}")
    object Characters: Destinations(route = "characters/{id}/{type}")
    object Reviews: Destinations(route = "reviews/{id}/{type}")
    object Review: Destinations(route = "review/{author}/{score}/{review}")
    object Staff: Destinations(route = "staff/{id}/{type}")
    object Recommendations: Destinations(route = "recommendations/{id}/{type}")
}
