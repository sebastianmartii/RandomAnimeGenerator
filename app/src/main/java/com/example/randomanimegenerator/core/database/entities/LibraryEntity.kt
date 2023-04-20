package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "library_table")
data class LibraryEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo(name = "mal_id") val malId: Int,
    val titleEng: String,
    val titleJap: String?,
    val type: String?,
    val studio: String?,
    val imageUrl: String,
    val source: String?,
    val score: String?,
    val rating: String?,
    val synopsis: String?,
    val status: String?,
    val year: Int?,
    val genrePrimary: String?,
    val genreSecondary: String? = null,
    val genreTertiary: String? = null,
    val demographic: String?,
    val episodes: String?,
    val authorPrimary: String?,
    val authorSecondary: String? = null,
    val volumes: Int?,
    val libraryType: String,
    val libraryStatus: String
)
