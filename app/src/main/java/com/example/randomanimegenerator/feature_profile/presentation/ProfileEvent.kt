package com.example.randomanimegenerator.feature_profile.presentation

sealed interface ProfileEvent {
    object SignOut : ProfileEvent
    object CloseSignOutDialog : ProfileEvent
    object OpenSignOutDialog : ProfileEvent
    object CloseChangeUserNameDialog : ProfileEvent
    object OpenChangeUserNameDialog : ProfileEvent
    data class ChangeUserName(val newUserName: String) : ProfileEvent
}