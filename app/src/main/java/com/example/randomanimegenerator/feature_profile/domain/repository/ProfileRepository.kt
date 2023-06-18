package com.example.randomanimegenerator.feature_profile.domain.repository

import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getEntriesCount(type: String): Flow<List<String>>

    suspend fun changeUserName(UID: String, userName: String)

    suspend fun changeProfilePicture(UID: String, profilePictureUrl: String)

    suspend fun addUser(UID: String, userName: String?, profilePictureUrl: String?)
}