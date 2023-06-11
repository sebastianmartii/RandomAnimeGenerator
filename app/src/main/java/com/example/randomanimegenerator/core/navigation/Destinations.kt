package com.example.randomanimegenerator.core.navigation

sealed class Destinations(val route: String) {

    object BottomBarGraph: Destinations(route = "bottom")
    object Generator: Destinations(route = "generator")
    object AnimeLibrary: Destinations(route = "anime")
    object MangaLibrary: Destinations(route = "manga")
    object DetailsNavGraph: Destinations(route = "details")
    object Details: Destinations(route = "details/{id}/{type}")
    object Characters: Destinations(route = "characters/{id}/{type}")
    object Reviews: Destinations(route = "reviews/{id}/{type}")
    object Review: Destinations(route = "review/{author}/{score}/{review}")
    object Staff: Destinations(route = "staff/{id}/{type}")
    object Recommendations: Destinations(route = "recommendations/{id}/{type}")
    object ProfileNavGraph: Destinations(route = "profile_nav_graph")
    object Profile: Destinations(route = "profile")
    object SignIn: Destinations(route = "sign_in")
    object SignUp: Destinations(route = "sign_up")
}
