package com.example.randomanimegenerator.feature_library.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.data.mappers.toLibraryModel
import com.example.randomanimegenerator.feature_library.domain.repository.LibraryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class LibraryViewModel @Inject constructor(
    private val repository: LibraryRepository
) : ViewModel() {

    private val _status = MutableStateFlow(LibraryStatus.ALL.name)

    private val _type = MutableStateFlow(Type.ANIME)
    val exposedType = _type.asStateFlow()

    private val _libraryContent = _type.flatMapLatest {type ->
        when(type) {
            Type.ANIME -> repository.getAnime()
            Type.MANGA -> repository.getManga()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    private val _state = MutableStateFlow(LibraryState())
    val state = combine(_state, _type, _libraryContent, _status) { state, type, content, status ->
        state.copy(
            content = content.map { it.toLibraryModel() },
            type = type,
            libraryStatus = status
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), LibraryState())

    fun setType(type: Type) {
        _type.value = type
    }

    fun selectStatus(status: String) {
        _status.value = status
    }

}