package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity

@Dao
interface MainInfoDao {

    @Upsert
    suspend fun insert(entity: MainInfoEntity)

    @Query("DELETE FROM library_table WHERE mal_id = :malId AND library_type = :type")
    suspend fun delete(malId: Int, type: String)

    @Query("SELECT * FROM library_table " +
            "WHERE mal_id = :malId AND library_type = :type")
    suspend fun getOne(malId: Int, type: String): MainInfoEntity
}