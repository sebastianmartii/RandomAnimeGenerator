package com.example.randomanimegenerator.core.databse.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey val id: Int? = null
)
