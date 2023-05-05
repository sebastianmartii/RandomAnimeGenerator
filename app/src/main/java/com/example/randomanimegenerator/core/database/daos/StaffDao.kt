package com.example.randomanimegenerator.core.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.randomanimegenerator.core.database.entities.StaffEntity

@Dao
interface StaffDao {

    @Upsert
    suspend fun upsertStaff(staff: List<StaffEntity>)

    @Query("DELETE FROM staff_table WHERE mal_id = :malId")
    suspend fun deleteStaff(malId: Int)

    @Query("SELECT * FROM staff_table WHERE mal_id = :malId")
    suspend fun getStaff(malId: Int): List<StaffEntity>
}