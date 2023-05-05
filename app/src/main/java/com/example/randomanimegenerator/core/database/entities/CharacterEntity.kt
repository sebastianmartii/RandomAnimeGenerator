package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("characters_table")
data class CharacterEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("mal_id") val malId: Int,
    @ColumnInfo("type") val type: String,
    @ColumnInfo("character_name") val characterName: String,
    @ColumnInfo("character_image") val characterImage: String,
    @ColumnInfo("character_role") val characterRole: String,
)
