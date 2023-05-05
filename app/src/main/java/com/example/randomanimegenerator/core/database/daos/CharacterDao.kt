package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.CharacterEntity

@Dao
interface CharacterDao {

    @Upsert
    suspend fun upsertCharacters(characters: List<CharacterEntity>)

    @Query("DELETE FROM characters_table WHERE mal_id = :malId AND type = :type")
    suspend fun deleteCharacters(malId: Int, type: String)

    @Query("SELECT * FROM characters_table WHERE mal_id = :malId AND type = :type")
    suspend fun getCharacters(malId: Int, type: String): List<CharacterEntity>
}