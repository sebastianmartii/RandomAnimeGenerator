package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MainInfoDao {

    @Upsert
    suspend fun insert(entity: MainInfoEntity)

    @Query("DELETE FROM library_table WHERE mal_id = :malId AND library_type = :type")
    suspend fun delete(malId: Int, type: String)

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = :isFavorite")
    fun getAll(type: String, isFavorite: Boolean): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE mal_id = :malId AND library_type = :type")
    suspend fun getOne(malId: Int, type: String): MainInfoEntity

    @Query("UPDATE library_table SET is_favorite = 1 WHERE mal_id = :malId AND library_type = :type")
    suspend fun updateEntry(malId: Int, type: String)
}