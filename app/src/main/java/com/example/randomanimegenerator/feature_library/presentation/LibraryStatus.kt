package com.example.randomanimegenerator.feature_library.presentation

enum class LibraryStatus {
    ALL, FINISHED, PLANNING, WATCHING, READING, PAUSED
}

val animeStatusList = listOf(
    LibraryStatus.ALL,
    LibraryStatus.FINISHED,
    LibraryStatus.PLANNING,
    LibraryStatus.WATCHING,
    LibraryStatus.PAUSED
)

val mangaStatusList = listOf(
    LibraryStatus.ALL,
    LibraryStatus.FINISHED,
    LibraryStatus.PLANNING,
    LibraryStatus.READING
)