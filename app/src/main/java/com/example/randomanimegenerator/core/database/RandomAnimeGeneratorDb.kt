package com.example.randomanimegenerator.core.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.randomanimegenerator.core.database.daos.CharacterDao
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.daos.RecommendationDao
import com.example.randomanimegenerator.core.database.daos.ReviewDao
import com.example.randomanimegenerator.core.database.daos.StaffDao
import com.example.randomanimegenerator.core.database.daos.UserDao
import com.example.randomanimegenerator.core.database.daos.UserFavoritesDao
import com.example.randomanimegenerator.core.database.entities.CharacterEntity
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.core.database.entities.RecommendationEntity
import com.example.randomanimegenerator.core.database.entities.ReviewEntity
import com.example.randomanimegenerator.core.database.entities.StaffEntity
import com.example.randomanimegenerator.core.database.entities.UserEntity
import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity

@Database(
    entities = [
        MainInfoEntity::class,
        CharacterEntity::class,
        RecommendationEntity::class,
        ReviewEntity::class,
        StaffEntity::class,
        UserEntity::class,
        UserFavoritesEntity::class],
    version = 6,
    autoMigrations = [
        AutoMigration(4,5, RandomAnimeGeneratorDb.Migration4To5::class),
        AutoMigration(5,6, RandomAnimeGeneratorDb.Migration5To6::class)
    ]
)
abstract class RandomAnimeGeneratorDb : RoomDatabase() {

    abstract val userFavoritesDao: UserFavoritesDao

    abstract val userDao: UserDao

    abstract val mainInfoDao: MainInfoDao

    abstract val characterDao: CharacterDao

    abstract val reviewDao: ReviewDao

    abstract val recommendationDao: RecommendationDao

    abstract val staffDao: StaffDao

    @DeleteColumn(tableName = "user_favorites_table", columnName = "main_info_id")
    class Migration5To6 : AutoMigrationSpec

    @DeleteColumn(tableName = "library_table", columnName = "library_status")
    class Migration4To5 : AutoMigrationSpec

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS user_table (user_UID TEXT NOT NULL PRIMARY KEY, user_name TEXT, profile_picture_url TEXT)")
            }
        }

        val MIGRATION_3_4 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE library_table DROP COLUMN user_uid")
                database.execSQL("ALTER TABLE library_table DROP COLUMN is_favorite")
                database.execSQL("CREATE TABLE IF NOT EXISTS user_favorites_table (id INTEGER PRIMARY KEY AUTOINCREMENT, entry_mal_id INTEGER NOT NULL, entry_type TEXT NOT NULL, user_uid TEXT NOT NULL)")
            }
        }
    }
}