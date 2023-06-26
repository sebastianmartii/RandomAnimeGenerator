package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("library_table")
data class MainInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("mal_id") val malId: Int,
    val title: String,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("large_image_url") val largeImageUrl: String,
    val synopsis: String,
    val type: String,
    val status: String,
    val score: Double,
    val genres: String,
    val themes: String,
    val demographic: String? = null,
    // manga params
    @ColumnInfo("manga_authors") val mangaAuthors: String? = null,
    @ColumnInfo("manga_chapters") val mangaChapters: Int? = null,
    // anime params
    val source: String? = null,
    val episodes: Int? = null,
    val studios: String? = null,
    // library params
    @ColumnInfo("library_type") val libraryType: String,
)
