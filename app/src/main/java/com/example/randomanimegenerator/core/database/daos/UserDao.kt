package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.randomanimegenerator.core.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun upsertUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("SELECT * FROM user_table WHERE user_UID = :UID")
    fun getUser(UID: String): Flow<UserEntity>

    @Query("UPDATE user_table SET user_name = :userName WHERE user_UID = :UID")
    suspend fun changeUserName(UID: String, userName: String?)

    @Query("UPDATE user_table SET profile_picture_url = :profilePictureUrl WHERE user_UID = :UID")
    suspend fun changeProfilePicture(UID: String, profilePictureUrl: String)
}