package com.example.randomanimegenerator.feature_library.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_generator.presentation.toType
import com.example.randomanimegenerator.feature_library.data.mappers.toLibraryModel
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableStateFlow(LibraryStatus.ALL)

    val type = savedStateHandle.get<String>("type")

    private val _libraryContent = _status.flatMapLatest { status ->
        when(status) {
            LibraryStatus.ALL -> repository.getAll(type!!)
            else -> repository.getAllByStatus(type!!, status.toStatusString())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(LibraryState())
    val state = combine(_status, _libraryContent, _state) { status, content, state ->
        state.copy(
            content = content.map { it.toLibraryModel() },
            type = type!!.toType(),
            libraryStatus = status
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LibraryState())

    fun onEvent(event: LibraryEvent) {
        when(event) {
            is LibraryEvent.ChangeStatus -> {
                _status.value = event.status
            }
        }
    }

}