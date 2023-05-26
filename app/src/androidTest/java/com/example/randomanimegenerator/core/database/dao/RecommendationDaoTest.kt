package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.RecommendationDao
import com.example.randomanimegenerator.core.database.entities.RecommendationEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
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
class RecommendationDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: RandomAnimeGeneratorDb
    private lateinit var dao: RecommendationDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.recommendationDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertRecommendations_recommendationsInsertedProperly() = runTest {
        val recommendation = RecommendationEntity(
            id = 1,
            malId = 1,
            type = "",
            recommendationId = 0,
            recommendationTitle = "",
            recommendationImage = ""
        )
        val recommendationList = listOf(
            recommendation
        )
        dao.upsertRecommendations(recommendationList)
        val recommendations = dao.getRecommendations(1, "")
        assertThat(recommendations).contains(recommendation)
    }

    @Test
    fun deleteRecommendations_recommendationsDeletedProperly() = runTest {
        val recommendation = RecommendationEntity(
            id = 1,
            malId = 1,
            type = "",
            recommendationId = 0,
            recommendationTitle = "",
            recommendationImage = ""
        )
        val recommendationList = listOf(
            recommendation
        )
        dao.upsertRecommendations(recommendationList)
        dao.deleteRecommendations(1, "")
        val recommendations = dao.getRecommendations(1, "")
        assertThat(recommendations).doesNotContain(recommendation)
    }

    @Test
    fun getRecommendationsAsFlow_recommendationsRetrievedProperly() = runTest {
        val recommendation = RecommendationEntity(
            id = 1,
            malId = 1,
            type = "",
            recommendationId = 0,
            recommendationTitle = "",
            recommendationImage = ""
        )
        val recommendation2 = RecommendationEntity(
            id = 2,
            malId = 1,
            type = "",
            recommendationId = 0,
            recommendationTitle = "",
            recommendationImage = ""
        )
        val recommendationList = listOf(
            recommendation,
            recommendation2
        )
        dao.upsertRecommendations(recommendationList)
        val flowOfRecommendations = dao.getRecommendationsAsFlow(1, "").first()
        assertThat(flowOfRecommendations).contains(recommendation)
        assertThat(flowOfRecommendations).contains(recommendation2)
    }
}