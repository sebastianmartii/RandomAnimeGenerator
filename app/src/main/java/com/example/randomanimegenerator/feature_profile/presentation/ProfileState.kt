package com.example.randomanimegenerator.feature_profile.presentation

import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

data class ProfileState(
    val userUID: String = "",
    val profilePictureUrl: String = "",
    val userName: String = "",
    val animeEntriesCount: List<EntriesCount> = emptyList(),
    val mangaEntriesCount: List<EntriesCount> = emptyList(),
    val openSignOutDialog: Boolean = false,
    val openChangeUserNameDialog: Boolean = false,
)

data class EntriesCount(
    val status: LibraryStatus,
    val count: Int,
)