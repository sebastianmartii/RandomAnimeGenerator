package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_UID") val userUID: String,
    @ColumnInfo(name = "user_name") val userName: String?,
    @ColumnInfo(name = "profile_picture_url") val profilePictureUrl: String?,
)
