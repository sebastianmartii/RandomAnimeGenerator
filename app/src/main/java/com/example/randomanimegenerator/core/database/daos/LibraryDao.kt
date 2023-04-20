package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.LibraryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {

    @Upsert
    suspend fun insert(entity: LibraryEntity)

    @Delete
    suspend fun delete(entity: LibraryEntity)

    @Query("SELECT * FROM library_table WHERE libraryType = :type")
    fun getAll(type: String): Flow<List<LibraryEntity>>

    @Query("SELECT * FROM library_table WHERE mal_id = :malId & libraryType = :type")
    fun getOne(malId: Int, type: String): Flow<LibraryEntity>
}