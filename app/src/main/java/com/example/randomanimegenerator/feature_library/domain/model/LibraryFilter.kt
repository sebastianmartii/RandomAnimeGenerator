package com.example.randomanimegenerator.feature_library.domain.model

import com.example.randomanimegenerator.feature_library.presentation.LibrarySortType
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

data class LibraryFilter(
    val libraryStatus: LibraryStatus = LibraryStatus.ALL,
    val librarySortType: LibrarySortType = LibrarySortType.OLDEST
)
