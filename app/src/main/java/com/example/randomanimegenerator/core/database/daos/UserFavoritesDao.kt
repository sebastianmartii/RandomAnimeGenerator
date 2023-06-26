package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserFavoritesDao {

    @Upsert
    suspend fun addToUserFavorites(userFavorite: UserFavoritesEntity)

    @Query(
        "DELETE FROM user_favorites_table " +
        "WHERE entry_mal_id = :malId AND entry_type = :type AND user_uid = :userUID"
    )
    suspend fun deleteFromUserFavorites(malId: Int, type: String, userUID: String)

    @Query(
        "SELECT entry_mal_id " +
        "FROM user_favorites_table " +
        "WHERE user_uid = :userUID AND entry_type = :type"
    )
    suspend fun getUserFavorites(type: String, userUID: String): List<Int>

    @Query("UPDATE user_favorites_table SET entry_status = :libraryStatus " +
            "WHERE entry_mal_id = :malId AND entry_type = :type AND user_uid = :userUID")
    suspend fun updateEntryStatus(malId: Int, type: String, libraryStatus: String, userUID: String)

    @Query("SELECT entry_status FROM user_favorites_table WHERE entry_type =:type AND user_uid = :userUID")
    fun getEntriesCount(type: String, userUID: String): Flow<List<String>>

    @Query("SELECT entry_status FROM user_favorites_table " +
            "WHERE entry_mal_id = :malId AND entry_type = :type AND user_uid = :userUID")
    fun getStatus(type: String, malId: Int, userUID: String): Flow<String>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID ORDER BY title ASC")
    fun getAllAZ(type: String, userUID: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID ORDER BY title DESC")
    fun getAllZA(type: String, userUID: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID ORDER BY id DESC")
    fun getAllNewest(type: String, userUID: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID ORDER BY id ASC")
    fun getAllOldest(type: String, userUID: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID AND entry_status = :status ORDER BY title ASC")
    fun getAllByStatusAZ(type: String, userUID: String, status: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID AND entry_status = :status  ORDER BY title DESC")
    fun getAllByStatusZA(type: String, userUID: String, status: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID AND entry_status = :status  ORDER BY id DESC")
    fun getAllByStatusNewest(type: String, userUID: String, status: String): Flow<List<UserFavoritesEntity>>

    @Query("SELECT * FROM user_favorites_table " +
            "WHERE entry_type = :type AND user_uid = :userUID AND entry_status = :status  ORDER BY id ASC")
    fun getAllByStatusOldest(type: String, userUID: String, status: String): Flow<List<UserFavoritesEntity>>
}