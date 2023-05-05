package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.RecommendationEntity

@Dao
interface RecommendationDao {

    @Upsert
    suspend fun upsertRecommendations(recommendations: List<RecommendationEntity>)

    @Query("DELETE FROM recommendations_table WHERE mal_id = :malId AND type = :type")
    suspend fun deleteRecommendations(malId: Int, type: String)

    @Query("SELECT * FROM recommendations_table WHERE mal_id = :malId AND type = :type")
    suspend fun getRecommendations(malId: Int, type: String): List<RecommendationEntity>
}