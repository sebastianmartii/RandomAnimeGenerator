package com.example.randomanimegenerator.feature_profile.presentation

import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus

data class ProfileState(
    val profilePictureUrl: String? = null,
    val userName: String? = null,
    val animeEntriesCount: List<EntriesCount> = emptyList(),
    val mangaEntriesCount: List<EntriesCount> = emptyList(),
    val openSignOutDialog: Boolean = false,
    val openChangeUserNameDialog: Boolean = false,
)

data class EntriesCount(
    val status: LibraryStatus,
    val count: Int,
)