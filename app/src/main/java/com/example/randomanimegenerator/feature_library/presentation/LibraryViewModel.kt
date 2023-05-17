package com.example.randomanimegenerator.feature_library.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_generator.presentation.toType
import com.example.randomanimegenerator.feature_library.data.mappers.toLibraryModel
import com.example.randomanimegenerator.feature_library.domain.model.LibraryFilter
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _libraryFilter = MutableStateFlow(LibraryFilter())

    val type = savedStateHandle.get<String>("type")

    private val _searchText = MutableStateFlow("")
    private val _isSearching = MutableStateFlow(false)

    private val _libraryContent = _libraryFilter.flatMapLatest { filter ->
        when(filter.libraryStatus) {
            LibraryStatus.ALL -> repository.getAll(type!!, filter.librarySortType)
            else -> repository.getAllByStatus(type!!, filter.libraryStatus.toStatusString(), filter.librarySortType)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _filteredContent = _searchText
        .combine(_libraryContent) { queryText, content ->
            if (queryText.isBlank()) {
                content
            } else {
                delay(2000)
                content.filter {
                    it.title.contains(queryText, ignoreCase = true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _libraryContent.value)

    private val _state = MutableStateFlow(LibraryState())
    val state = combine(_libraryFilter, _filteredContent, _state, _isSearching, _searchText) { filter, content, state, isSearching, text ->
        state.copy(
            content = content.map { it.toLibraryModel() },
            type = type!!.toType(),
            libraryStatus = filter.libraryStatus,
            librarySortType = filter.librarySortType,
            isSearching = isSearching,
            searchText = text
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LibraryState())

    fun onEvent(event: LibraryEvent) {
        when(event) {
            is LibraryEvent.ChangeStatus -> {
                _libraryFilter.update {
                    it.copy(
                        libraryStatus = event.status,
                    )
                }
            }
            is LibraryEvent.ChangeSortType -> {
                _libraryFilter.update {
                    it.copy(
                        librarySortType = event.sortType
                    )
                }
            }
            LibraryEvent.Search -> {
                _isSearching.update { !it }
                _searchText.update { "" }
            }
        }
    }

    fun onSearchTextChanges(text: String) {
        _searchText.update { text }
    }
}