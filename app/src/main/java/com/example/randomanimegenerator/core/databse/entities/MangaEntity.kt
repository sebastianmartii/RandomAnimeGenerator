package com.example.randomanimegenerator.core.databse.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "manga_table")
data class MangaEntity(
    @PrimaryKey val id: Int? = null
)