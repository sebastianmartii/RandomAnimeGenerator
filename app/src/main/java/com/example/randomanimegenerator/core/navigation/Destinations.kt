package com.example.randomanimegenerator.core.navigation

sealed class Destinations(val route: String) {

    object BottomBarGraph: Destinations(route = "bottom")
    object Generator: Destinations(route = "generator")
    object AnimeLibrary: Destinations(route = "anime")
    object MangaLibrary: Destinations(route = "manga")
    object DetailsNavGraph: Destinations(route = "details_nav_graph")
    object Details: Destinations(route = "details/{id}/{type}")
    object DetailsWithoutArguments: Destinations(route = "details")
    object Characters: Destinations(route = "characters")
    object Reviews: Destinations(route = "reviews")
    object Review: Destinations(route = "review")
    object Staff: Destinations(route = "staff")
    object Recommendations: Destinations(route = "recommendations")
    object ProfileNavGraph: Destinations(route = "profile_nav_graph")
    object Profile: Destinations(route = "profile")
    object SignIn: Destinations(route = "sign_in")
    object SignUp: Destinations(route = "sign_up")
}
