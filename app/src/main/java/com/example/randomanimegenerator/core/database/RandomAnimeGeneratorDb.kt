package com.example.randomanimegenerator.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.randomanimegenerator.core.database.daos.CharacterDao
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.daos.RecommendationDao
import com.example.randomanimegenerator.core.database.daos.ReviewDao
import com.example.randomanimegenerator.core.database.daos.StaffDao
import com.example.randomanimegenerator.core.database.daos.UserDao
import com.example.randomanimegenerator.core.database.entities.CharacterEntity
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.core.database.entities.RecommendationEntity
import com.example.randomanimegenerator.core.database.entities.ReviewEntity
import com.example.randomanimegenerator.core.database.entities.StaffEntity
import com.example.randomanimegenerator.core.database.entities.UserEntity

@Database(
    entities = [MainInfoEntity::class, CharacterEntity::class, RecommendationEntity::class, ReviewEntity::class, StaffEntity::class, UserEntity::class],
    version = 2,
)
abstract class RandomAnimeGeneratorDb : RoomDatabase() {

    abstract val userDao: UserDao

    abstract val mainInfoDao: MainInfoDao

    abstract val characterDao: CharacterDao

    abstract val reviewDao: ReviewDao

    abstract val recommendationDao: RecommendationDao

    abstract val staffDao: StaffDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS user_table (user_UID TEXT NOT NULL PRIMARY KEY, user_name TEXT, profile_picture_url TEXT)")
            }
        }
    }
}