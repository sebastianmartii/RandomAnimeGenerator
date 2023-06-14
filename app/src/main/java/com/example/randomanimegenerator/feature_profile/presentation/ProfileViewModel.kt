package com.example.randomanimegenerator.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationClient: AuthenticationClient,
    mainInfoDao: MainInfoDao
) : ViewModel() {

    private val _animeEntriesCount = mainInfoDao.getEntriesCount(Type.ANIME.toTypeString())
    private val _mangaEntriesCount = mainInfoDao.getEntriesCount(Type.MANGA.toTypeString())

    private val _state = MutableStateFlow(ProfileState())
    val state = combine(
        _state,
        _animeEntriesCount,
        _mangaEntriesCount
    ) { state, animeEntriesCount, mangaEntriesCount ->
        state.copy(
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
    }

    init {
        authenticationClient.getSignedInUser().run {
            _state.update {
                it.copy(
                    profilePictureUrl = this?.profilePictureUrl
                        ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png",
                    userName = this?.userName
                )
            }
        }
    }

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
                    authenticationClient.signOut()
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
                _state.update {
                    it.copy(
                        userName = event.newUserName
                    )
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
        }
    }
}