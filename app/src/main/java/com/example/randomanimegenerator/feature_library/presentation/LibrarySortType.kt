package com.example.randomanimegenerator.feature_library.presentation

enum class LibrarySortType {
    A_Z, Z_A, NEWEST, OLDEST
}

val librarySortType = listOf(
    LibrarySortType.OLDEST,
    LibrarySortType.NEWEST,
    LibrarySortType.A_Z,
    LibrarySortType.Z_A
)