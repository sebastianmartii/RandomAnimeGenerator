package com.example.randomanimegenerator.feature_library.presentation

import com.example.randomanimegenerator.feature_generator.presentation.Type

sealed interface LibraryEvent {
    data class ChangeStatus(val status: LibraryStatus) : LibraryEvent
    data class ChangeSortType(val sortType: LibrarySortType) : LibraryEvent
    data class ChangeSearchText(val query: String) : LibraryEvent
    data class SetType(val type: Type) : LibraryEvent
    data class ChangeFilterType(val filterType: FilterType) : LibraryEvent
    object Search : LibraryEvent
    object ClearTextField : LibraryEvent
    object OpenFilterMenu : LibraryEvent
    object CloseFilterMenu : LibraryEvent
}