package com.example.randomanimegenerator.core.constants

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PlayCircle
import com.example.randomanimegenerator.core.navigation.BottomNavItem
import com.example.randomanimegenerator.core.navigation.Destinations
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

const val BASE_URL = "https://api.jikan.moe"

val listOfTypes = listOf("Anime", "Manga")

val listOfScores = listOf("9", "8", "7")

val listOfAmounts = listOf("1", "25")

val minScoreToPageMapAnime = mapOf(9 to 1, 8 to 25, 7 to 172)

val minScoreToPageMapManga = mapOf(9 to 1, 8 to 32, 7 to 300)

val navigationItems = listOf(
    BottomNavItem(
        name = "Generator",
        route = Destinations.Generator.route,
        icon = Icons.Default.PlayCircle
    ),
    BottomNavItem(
        name = "Anime",
        route = Destinations.AnimeLibrary.route,
        icon = Icons.Default.Movie
    ),
    BottomNavItem(
        name = "Manga",
        route = Destinations.MangaLibrary.route,
        icon = Icons.Default.Book
    ),
    BottomNavItem(
        name = "Profile",
        route = Destinations.Profile.route,
        icon = Icons.Default.AccountCircle
    )
)

val animeDetailsStatus = listOf(
    LibraryStatus.PLANNING,
    LibraryStatus.FINISHED,
    LibraryStatus.WATCHING,
    LibraryStatus.PAUSED
)

val mangaDetailStatus = listOf(
    LibraryStatus.PLANNING,
    LibraryStatus.FINISHED,
    LibraryStatus.READING
)