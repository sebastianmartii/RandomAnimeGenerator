package com.example.randomanimegenerator.feature_library.data.mappers

import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_library.domain.model.LibraryModel

fun MainInfoEntity.toLibraryModel(): LibraryModel {
    return LibraryModel(
        title = title,
        imageUrl = imageUrl,
        malId = malId
    )
}
