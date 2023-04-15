package com.example.randomanimegenerator.core.databse.daos

import androidx.room.*
import com.example.randomanimegenerator.core.databse.entities.GeneratorEntity

@Dao
interface GeneratorDao {

    @Delete
    suspend fun delete(entity: GeneratorEntity)

    @Upsert
    suspend fun insert(entity: GeneratorEntity)

    @Query("SELECT * FROM generator_table WHERE mal_id = :malId")
    suspend fun getGeneratorEntity(malId: Int): GeneratorEntity
}