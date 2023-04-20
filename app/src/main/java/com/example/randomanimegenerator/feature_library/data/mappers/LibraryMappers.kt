package com.example.randomanimegenerator.feature_library.data.mappers

import com.example.randomanimegenerator.core.database.entities.LibraryEntity
import com.example.randomanimegenerator.feature_library.domain.model.LibraryModel

fun LibraryEntity.toLibraryModel(): LibraryModel {
    return LibraryModel(
        title = titleEng,
        imageUrl = imageUrl,
        malId = malId
    )
}
