package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.ReviewEntity

@Dao
interface ReviewDao {

    @Upsert
    suspend fun upsertReviews(reviews: List<ReviewEntity>)

    @Query("DELETE FROM reviews_table WHERE mal_id = :malId AND type = :type")
    suspend fun deleteReviews(malId: Int, type: String)

    @Query("SELECT * FROM reviews_table WHERE mal_id = :malId AND type = :type")
    suspend fun getReviews(malId: Int, type: String): List<ReviewEntity>
}