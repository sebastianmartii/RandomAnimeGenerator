package com.example.randomanimegenerator.core.navigation

sealed class Destinations(val route: String) {

    object BottomBarGraph: Destinations(route = "bottom")
    object Generator: Destinations(route = "generator")
    object AnimeLibrary: Destinations(route = "anime")
    object MangaLibrary: Destinations(route = "manga")
    object DetailsNavGraph: Destinations(route = "details")
    object Details: Destinations(route = "details/{id}/{type}")
    object Description: Destinations(route = "description/{title}/{image}/{synopsis}")
    object Characters: Destinations(route = "characters/{id}/{type}")
    object Reviews: Destinations(route = "reviews/{id}/{type}")
    object Staff: Destinations(route = "staff/{id}")
    object Recommendations: Destinations(route = "recommendations/{id}/{type}")
}
