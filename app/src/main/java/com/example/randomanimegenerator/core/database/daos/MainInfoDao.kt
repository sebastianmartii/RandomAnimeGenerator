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

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 ORDER BY id ASC")
    fun getAll(type: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 ORDER BY id DESC")
    fun getAllNewest(type: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 ORDER BY title ASC")
    fun getAllAZ(type: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 ORDER BY title DESC")
    fun getAllZA(type: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 AND library_status = :libraryStatus ORDER BY id ASC")
    fun getAllByStatus(type: String, libraryStatus: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 AND library_status = :libraryStatus ORDER BY id DESC")
    fun getAllByStatusNewest(type: String, libraryStatus: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 AND library_status = :libraryStatus ORDER BY title ASC")
    fun getAllByStatusAZ(type: String, libraryStatus: String): Flow<List<MainInfoEntity>>

    @Query("SELECT * FROM library_table WHERE library_type = :type AND is_favorite = 1 AND library_status = :libraryStatus ORDER BY title DESC")
    fun getAllByStatusZA(type: String, libraryStatus: String): Flow<List<MainInfoEntity>>

    @Query("SELECT library_status FROM library_table WHERE library_type = :type AND is_favorite = 1")
    fun getEntriesCount(type: String): Flow<List<String>>

    @Query("SELECT * FROM library_table WHERE mal_id = :malId AND library_type = :type")
    suspend fun getOne(malId: Int, type: String): MainInfoEntity

    @Query("UPDATE library_table SET is_favorite = :isFavorite WHERE mal_id = :malId AND library_type = :type")
    suspend fun updateEntry(malId: Int, type: String, isFavorite: Boolean)

    @Query("UPDATE library_table SET library_status = :libraryStatus WHERE mal_id = :malId AND library_type = :type")
    suspend fun updateEntryStatus(malId: Int, type: String, libraryStatus: String)
}