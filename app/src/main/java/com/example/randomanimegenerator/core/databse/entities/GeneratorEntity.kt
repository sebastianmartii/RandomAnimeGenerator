package com.example.randomanimegenerator.core.databse.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

@Entity(tableName = "generator_table")
data class GeneratorEntity(
    @PrimaryKey val id: Int? = null,
    @ColumnInfo("mal_id") val malId: Int,
    val title: String,
    val imageUrl: String
) {
    fun toGeneratorModel(): GeneratorModel {
        return GeneratorModel(
            title = title,
            imageUrl = imageUrl
        )
    }
}