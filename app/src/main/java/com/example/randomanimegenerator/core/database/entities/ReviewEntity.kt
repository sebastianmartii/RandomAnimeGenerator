package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("reviews_table")
data class ReviewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("mal_id") val malId: Int,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("review_author") val reviewAuthor: String,
    @ColumnInfo("review_score") val reviewScore: Int,
    @ColumnInfo("review") val review: String,
)
