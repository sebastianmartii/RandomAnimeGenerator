package com.example.randomanimegenerator.feature_library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.data.mappers.toLibraryModel
import com.example.randomanimegenerator.feature_library.domain.model.LibraryFilter
import com.example.randomanimegenerator.feature_library.domain.use_case.GetAllByStatusUseCase
import com.example.randomanimegenerator.feature_library.domain.use_case.GetAllUseCase
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val getAllUseCase: GetAllUseCase,
    private val getAllByStatusUseCase: GetAllByStatusUseCase,
    authClient: AuthenticationClient
) : ViewModel() {

    private val _libraryFilter = MutableStateFlow(LibraryFilter())
    val libraryFilter = _libraryFilter.asStateFlow()

    val userUID = authClient.getSignedInUser()?.userId ?: ""

    val type = MutableStateFlow(Type.ANIME)

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _libraryContent = _libraryFilter.flatMapLatest { filter ->
        when (filter.libraryStatus) {
            LibraryStatus.ALL -> getAllUseCase(type.value.toTypeString(), filter.librarySortType, userUID)
            else -> getAllByStatusUseCase(
                type.value.toTypeString(),
                filter.libraryStatus.toStatusString(),
                filter.librarySortType,
                userUID
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _filteredContent = _searchText
        .combine(_libraryContent) { queryText, content ->
            if (queryText.isBlank()) {
                content
            } else {
                content.filter {
                    it.title.contains(queryText, ignoreCase = true)
                }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), _libraryContent.value)

    private val _state = MutableStateFlow(LibraryState())
    val state = combine(
        _libraryFilter,
        _filteredContent,
        _state,
        _isSearching,
        _searchText
    ) { filter, content, state, isSearching, text ->
        state.copy(
            content = content.map { it.toLibraryModel() },
            type = type.value,
            libraryStatus = filter.libraryStatus,
            librarySortType = filter.librarySortType,
            isSearching = isSearching,
            searchText = text
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LibraryState())

    private val _channel = Channel<UiEvent>()
    val eventFlow = _channel.receiveAsFlow()

    fun onEvent(event: LibraryEvent) {
        when (event) {
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

            is LibraryEvent.Search -> {
                _isSearching.update { !it }
                _searchText.update { "" }
            }

            is LibraryEvent.ClearTextField -> {
                _searchText.update { "" }
            }

            is LibraryEvent.ChangeSearchText -> {
                _searchText.update { event.query }
            }

            is LibraryEvent.SetType -> {
                type.value = event.type
            }

            is LibraryEvent.ChangeFilterType -> {
                _state.update {
                    it.copy(
                        filterType = event.filterType
                    )
                }
            }

            is LibraryEvent.CloseFilterMenu -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.CloseFilterMenu)
                }
            }

            is LibraryEvent.OpenFilterMenu -> {
                viewModelScope.launch {
                    _channel.send(UiEvent.OpenFilterMenu)
                }
            }
        }
    }

    sealed class UiEvent {
        object OpenFilterMenu : UiEvent()
        object CloseFilterMenu : UiEvent()
    }
}