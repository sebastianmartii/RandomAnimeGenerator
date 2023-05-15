package com.example.randomanimegenerator.feature_library.presentation

sealed interface LibraryEvent {
    data class ChangeStatus(val status: LibraryStatus) : LibraryEvent
    data class ChangeSortType(val sortType: LibrarySortType) : LibraryEvent
}