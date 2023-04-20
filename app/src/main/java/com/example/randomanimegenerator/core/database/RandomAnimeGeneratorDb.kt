package com.example.randomanimegenerator.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.randomanimegenerator.core.database.daos.LibraryDao
import com.example.randomanimegenerator.core.database.entities.LibraryEntity

@Database(
    entities = [LibraryEntity::class],
    version = 1
)
abstract class RandomAnimeGeneratorDb : RoomDatabase() {

    abstract val libraryDao: LibraryDao
}