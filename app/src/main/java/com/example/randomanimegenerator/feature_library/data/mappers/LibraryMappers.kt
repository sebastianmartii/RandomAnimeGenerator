package com.example.randomanimegenerator.feature_library.data.mappers

import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import com.example.randomanimegenerator.feature_library.domain.model.LibraryModel

fun UserFavoritesEntity.toLibraryModel(): LibraryModel {
    return LibraryModel(
        title = title,
        imageUrl = imageUrl,
        malId = entryMalID
    )
}