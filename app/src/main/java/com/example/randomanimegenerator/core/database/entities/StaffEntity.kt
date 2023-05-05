package com.example.randomanimegenerator.core.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("staff_table")
data class StaffEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo("mal_id") val malId: Int,
    @ColumnInfo("staff_member_name") val staffMemberName: String,
    @ColumnInfo("staff_member_image") val staffMemberImage: String,
    @ColumnInfo("staff_member_positions") val staffMemberPositions: String,
)
