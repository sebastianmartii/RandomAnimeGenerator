package com.example.randomanimegenerator.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomanimegenerator.core.database.daos.CharacterDao
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.daos.RecommendationDao
import com.example.randomanimegenerator.core.database.daos.ReviewDao
import com.example.randomanimegenerator.core.database.daos.StaffDao
import com.example.randomanimegenerator.core.database.entities.CharacterEntity
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.core.database.entities.RecommendationEntity
import com.example.randomanimegenerator.core.database.entities.ReviewEntity
import com.example.randomanimegenerator.core.database.entities.StaffEntity

@Database(
    entities = [MainInfoEntity::class, CharacterEntity::class, RecommendationEntity::class, ReviewEntity::class, StaffEntity::class],
    version = 1
)
abstract class RandomAnimeGeneratorDb : RoomDatabase() {

    abstract val mainInfoDao: MainInfoDao

    abstract val characterDao: CharacterDao

    abstract val reviewDao: ReviewDao

    abstract val recommendationDao: RecommendationDao

    abstract val staffDao: StaffDao
}