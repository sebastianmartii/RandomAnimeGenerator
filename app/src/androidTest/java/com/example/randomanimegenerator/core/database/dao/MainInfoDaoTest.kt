package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@OptIn(ExperimentalCoroutinesApi::class)
@SmallTest
@HiltAndroidTest
class MainInfoDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: RandomAnimeGeneratorDb
    private lateinit var dao: MainInfoDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.mainInfoDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertSingleMainInfo_mainInfoInsertedProperly() = runTest {
        val mainInfo = MainInfoEntity(
            id = 1,
            malId = 1,
            title = "title 1",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
        )
        dao.insert(mainInfo)
        val info = dao.getOne(1, "")
        assertThat(info).isEqualTo(mainInfo)
    }

    @Test
    fun deleteMainInfo_mainInfoInsertedProperly() = runTest {
        val mainInfo = MainInfoEntity(
            id = 1,
            malId = 1,
            title = "title 1",
            imageUrl = "",
            largeImageUrl = "",
            synopsis = "",
            type = "",
            status = "",
            score = 8.0,
            genres = "",
            themes = "",
            libraryType = "",
        )
        dao.insert(mainInfo)
        dao.delete(1, "")
        val info = dao.getOne(1, "")
        assertThat(info).isNotEqualTo(mainInfo)
    }
}