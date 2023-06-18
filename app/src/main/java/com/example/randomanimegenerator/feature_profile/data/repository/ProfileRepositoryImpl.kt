package com.example.randomanimegenerator.feature_profile.data.repository

import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.daos.UserDao
import com.example.randomanimegenerator.feature_profile.data.mappers.toUserEntity
import com.example.randomanimegenerator.feature_profile.domain.repository.ProfileRepository
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import kotlinx.coroutines.flow.Flow

class ProfileRepositoryImpl(
    private val mainInfoDao: MainInfoDao,
    private val userDao: UserDao,
    private val authenticationClient: AuthenticationClient
) : ProfileRepository {

    override fun getEntriesCount(type: String): Flow<List<String>> = mainInfoDao.getEntriesCount(type)

    override suspend fun addUser(UID: String, userName: String?, profilePictureUrl: String?) {
        val authUser = authenticationClient.getSignedInUser()
        userDao.upsertUser(authUser.toUserEntity())
    }

    override suspend fun changeUserName(UID: String, userName: String) {
        userDao.changeUserName(UID, userName)
    }

    override suspend fun changeProfilePicture(UID: String, profilePictureUrl: String) {
        userDao.changeProfilePicture(UID, profilePictureUrl)
    }
}