package com.example.randomanimegenerator.feature_profile.data.mappers

import com.example.randomanimegenerator.core.database.entities.UserEntity
import com.example.randomanimegenerator.feature_profile.domain.model.UserData


fun UserData?.toUserEntity(): UserEntity {
    return UserEntity(
        userName = this?.userName ?: "UserName",
        userUID = this!!.userId,
        profilePictureUrl = this.profilePictureUrl ?: "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png"
    )
}

fun UserEntity?.toUserData(): UserData {
    return UserData(
        userId = this?.userUID ?: "",
        userName = this?.userName ?: "",
        profilePictureUrl = this?.profilePictureUrl ?: ""
    )
}