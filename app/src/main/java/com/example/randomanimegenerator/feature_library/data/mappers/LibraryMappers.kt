package com.example.randomanimegenerator.feature_library.data.mappers

import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import com.example.randomanimegenerator.feature_library.domain.model.LibraryModel
import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType

fun UserFavoritesEntity.toLibraryModel(): LibraryModel {
    return LibraryModel(
        title = title,
        imageUrl = imageUrl,
        malId = entryMalID
    )
}

fun LibrarySortType.toSortTypeString(): String {
    return when(this) {
        LibrarySortType.A_Z -> "A - Z"
        LibrarySortType.Z_A -> "Z - A"
        LibrarySortType.NEWEST -> "Newest - Oldest"
        LibrarySortType.OLDEST -> "Oldest - Newest"
    }
}