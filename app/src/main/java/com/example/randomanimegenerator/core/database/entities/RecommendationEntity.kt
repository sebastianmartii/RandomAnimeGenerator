package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("recommendations_table")
data class RecommendationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("mal_id") val malId: Int,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("recommendation_title") val recommendationTitle: String,
    @ColumnInfo("recommendation_image") val recommendationImage: String,
)
