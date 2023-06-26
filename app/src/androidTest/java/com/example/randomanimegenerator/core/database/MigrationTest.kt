package com.example.randomanimegenerator.core.database

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val DB_NAME = "test_db"

@RunWith(AndroidJUnit4::class)
class MigrationTest {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        RandomAnimeGeneratorDb::class.java
    )

    @Test
    fun testAllMigrations() {
        helper.createDatabase(DB_NAME, 1).apply {
            close()
        }

        Room.databaseBuilder(
            InstrumentationRegistry.getInstrumentation().targetContext,
            RandomAnimeGeneratorDb::class.java,
            DB_NAME
        ).addMigrations(RandomAnimeGeneratorDb.MIGRATION_1_2, RandomAnimeGeneratorDb.MIGRATION_3_4).fallbackToDestructiveMigration().build().apply {
            openHelper.writableDatabase.close()
        }
    }
}