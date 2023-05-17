package com.example.randomanimegenerator.feature_library.presentation

import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.domain.model.LibraryModel

data class LibraryState(
    val content: List<LibraryModel> = emptyList(),
    val type: Type = Type.ANIME,
    val searchText: String = "",
    val isSearching: Boolean = false,
    val libraryStatus: LibraryStatus = LibraryStatus.ALL,
    val librarySortType: LibrarySortType = LibrarySortType.OLDEST,
)
