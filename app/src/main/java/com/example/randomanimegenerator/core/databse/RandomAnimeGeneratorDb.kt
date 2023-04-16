package com.example.randomanimegenerator.core.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomanimegenerator.core.databse.daos.AnimeDao
import com.example.randomanimegenerator.core.databse.daos.MangaDao
import com.example.randomanimegenerator.core.databse.entities.AnimeEntity
import com.example.randomanimegenerator.core.databse.entities.MangaEntity

@Database(
    entities = [AnimeEntity::class, MangaEntity::class],
    exportSchema = false,
    version = 1
)
abstract class RandomAnimeGeneratorDb : RoomDatabase() {

    abstract val animeDao: AnimeDao

    abstract val mangaDao: MangaDao
}