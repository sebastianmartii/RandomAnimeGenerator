package com.example.randomanimegenerator.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.database.daos.UserDao
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
import com.example.randomanimegenerator.feature_profile.data.mappers.toUserData
import com.example.randomanimegenerator.feature_profile.domain.repository.ProfileRepository
import com.example.randomanimegenerator.feature_profile.domain.use_cases.GetEntriesCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationClientImpl: AuthenticationClientImpl,
    private val repo: ProfileRepository,
    userDao: UserDao,
    getEntriesCountUseCase: GetEntriesCountUseCase,
) : ViewModel() {

    private val _animeEntriesCount = getEntriesCountUseCase(Type.ANIME.toTypeString())
    private val _mangaEntriesCount = getEntriesCountUseCase(Type.MANGA.toTypeString())

    private val _userUID = authenticationClientImpl.getSignedInUser()?.userId
    private val _currentUser = userDao.getUser(_userUID ?: "").flatMapLatest {
        flow {
            emit(it.toUserData())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    private val _state = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        _currentUser,
        _animeEntriesCount,
        _mangaEntriesCount
    ) { state, user, animeEntriesCount, mangaEntriesCount ->
        state.copy(
            userUID = user?.userId ?: "",
            userName = user?.userName ?: "",
            profilePictureUrl = user?.profilePictureUrl ?: "",
            animeEntriesCount = listOf(
                EntriesCount(LibraryStatus.ALL, animeEntriesCount.size),
                EntriesCount(
                    LibraryStatus.PLANNING,
                    animeEntriesCount.filter { it == LibraryStatus.PLANNING.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.FINISHED,
                    animeEntriesCount.filter { it == LibraryStatus.FINISHED.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.WATCHING,
                    animeEntriesCount.filter { it == LibraryStatus.WATCHING.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.READING,
                    animeEntriesCount.filter { it == LibraryStatus.READING.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.PAUSED,
                    animeEntriesCount.filter { it == LibraryStatus.PAUSED.toStatusString() }.size
                ),
            ),
            mangaEntriesCount = listOf(
                EntriesCount(LibraryStatus.ALL, mangaEntriesCount.size),
                EntriesCount(
                    LibraryStatus.PLANNING,
                    mangaEntriesCount.filter { it == LibraryStatus.PLANNING.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.FINISHED,
                    mangaEntriesCount.filter { it == LibraryStatus.FINISHED.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.WATCHING,
                    mangaEntriesCount.filter { it == LibraryStatus.WATCHING.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.READING,
                    mangaEntriesCount.filter { it == LibraryStatus.READING.toStatusString() }.size
                ),
                EntriesCount(
                    LibraryStatus.PAUSED,
                    mangaEntriesCount.filter { it == LibraryStatus.PAUSED.toStatusString() }.size
                ),
            ),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ProfileState())

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.CloseSignOutDialog -> {
                _state.update {
                    it.copy(
                        openSignOutDialog = false
                    )
                }
            }

            ProfileEvent.SignOut -> {
                viewModelScope.launch {
                    authenticationClientImpl.signOut()
                }
            }

            ProfileEvent.OpenSignOutDialog -> {
                _state.update {
                    it.copy(
                        openSignOutDialog = true
                    )
                }
            }

            is ProfileEvent.ChangeUserName -> {
                viewModelScope.launch {
                    repo.changeUserName(_userUID!!, event.newUserName)
                }
            }

            ProfileEvent.CloseChangeUserNameDialog -> {
                _state.update {
                    it.copy(
                        openChangeUserNameDialog = false
                    )
                }
            }

            ProfileEvent.OpenChangeUserNameDialog -> {
                _state.update {
                    it.copy(
                        openChangeUserNameDialog = true
                    )
                }
            }

            is ProfileEvent.ChangeProfilePicture -> {
                viewModelScope.launch {
                    repo.changeProfilePicture(
                        _userUID!!,
                        event.newProfilePictureUrl
                    )
                }
            }
        }
    }
}