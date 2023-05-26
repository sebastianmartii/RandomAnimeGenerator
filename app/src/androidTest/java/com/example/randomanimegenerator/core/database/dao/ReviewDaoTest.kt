package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.ReviewDao
import com.example.randomanimegenerator.core.database.entities.ReviewEntity
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
class ReviewDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: RandomAnimeGeneratorDb
    private lateinit var dao: ReviewDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.reviewDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertReviews_reviewsInsertedProperly() = runTest {
        val review = ReviewEntity(
            id = 1,
            malId = 1,
            type = "",
            review = "",
            reviewScore = 0,
            reviewAuthor = ""
        )
        val reviewList = listOf(
            review
        )
        dao.upsertReviews(reviewList)
        val reviews = dao.getReviews(1, "")
        assertThat(reviews).contains(review)
    }

    @Test
    fun deleteReviews_reviewsDeletedProperly() = runTest {
        val review = ReviewEntity(
            id = 1,
            malId = 1,
            type = "",
            review = "",
            reviewScore = 0,
            reviewAuthor = ""
        )
        val reviewList = listOf(
            review
        )
        dao.upsertReviews(reviewList)
        dao.deleteReviews(1, "")
        val reviews = dao.getReviews(1, "")
        assertThat(reviews).doesNotContain(review)
    }

    @Test
    fun getReviewsAsFlow_reviewsRetrievedProperly() = runTest {
        val review = ReviewEntity(
            id = 1,
            malId = 1,
            type = "",
            review = "",
            reviewScore = 0,
            reviewAuthor = ""
        )
        val review2 = ReviewEntity(
            id = 2,
            malId = 1,
            type = "",
            review = "",
            reviewScore = 0,
            reviewAuthor = ""
        )
        val reviewList = listOf(
            review,
            review2
        )
        dao.upsertReviews(reviewList)
        val flowOfReviews = dao.getReviewsAsFlow(1, "").first()
        assertThat(flowOfReviews).contains(review)
        assertThat(flowOfReviews).contains(review2)
    }
}