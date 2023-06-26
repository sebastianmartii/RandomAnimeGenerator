package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("user_favorites_table")
data class UserFavoritesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(defaultValue = "") val title: String,
    @ColumnInfo(defaultValue = "") val imageUrl: String,
    @ColumnInfo("entry_mal_id") val entryMalID: Int,
    @ColumnInfo("entry_type") val entryType: String,
    @ColumnInfo("user_uid") val userUID: String,
    @ColumnInfo("entry_status", defaultValue = "planning") val entryStatus: String
)
