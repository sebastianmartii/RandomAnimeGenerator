package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.UserFavoritesDao
import com.example.randomanimegenerator.core.database.entities.UserFavoritesEntity
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
class UserFavoritesDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: RandomAnimeGeneratorDb
    private lateinit var dao: UserFavoritesDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.userFavoritesDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun addUserFavorite_EntryAddedCorrectly() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "",
            imageUrl = "",
            entryStatus = "",
            entryType = "",
            entryMalID = 123,
            userUID = "uid1"
        )
        dao.addToUserFavorites(userFavorite)
        val returnedUserFavorite = dao.getUserFavorites("", "uid1")
        assertThat(returnedUserFavorite).contains(123)
    }

    @Test
    fun deleteUserFavorite_EntryDeletedCorrectly() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "",
            imageUrl = "",
            entryStatus = "",
            entryType = "",
            entryMalID = 123,
            userUID = "uid1"
        )
        dao.addToUserFavorites(userFavorite)
        dao.deleteFromUserFavorites(123, "", "uid1")
        val returnedUserFavorite = dao.getUserFavorites("", "uid1")
        assertThat(returnedUserFavorite).doesNotContain(123)
    }

    @Test
    fun updateUserFavoriteStatus_StatusUpdatedCorrectly() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "",
            imageUrl = "",
            entryStatus = "planning",
            entryType = "",
            entryMalID = 1,
            userUID = "uid1"
        )
        dao.addToUserFavorites(userFavorite)
        dao.updateEntryStatus(1, "", "finished", "uid1")
        val returnedUserFavorite = dao.getAllAZ("", "uid1").first()
        assertThat(returnedUserFavorite.first().entryStatus).isEqualTo("finished")
    }


    @Test
    fun getEntriesCount_EntriesCountReturnedCorrectly() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "",
            imageUrl = "",
            entryStatus = "planning",
            entryType = "",
            entryMalID = 1,
            userUID = "uid1"
        )
        val userFavorite2 = UserFavoritesEntity(
            id = 2,
            title = "",
            imageUrl = "",
            entryStatus = "finished",
            entryType = "",
            entryMalID = 2,
            userUID = "uid1"
        )
        dao.addToUserFavorites(userFavorite)
        dao.addToUserFavorites(userFavorite2)
        val entriesCountList = dao.getEntriesCount("", "uid1").first()
        assertThat(entriesCountList).contains("planning")
        assertThat(entriesCountList).contains("finished")
    }

    @Test
    fun getStatus_StatusReturnedCorrectly() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "",
            imageUrl = "",
            entryStatus = "paused",
            entryType = "",
            entryMalID = 1,
            userUID = "uid1"
        )
        dao.addToUserFavorites(userFavorite)
        val status = dao.getStatus("", 1, "uid1").first()
        assertThat(status).isEqualTo("paused")
    }

    @Test
    fun getAllUserFavoritesByStatus_UserFavoritesReturnedCorrectlyInCorrectOrder() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "",
            imageUrl = "",
            entryStatus = "",
            entryType = "",
            entryMalID = 1,
            userUID = "uid1"
        )
        val userFavorite2 = UserFavoritesEntity(
            id = 2,
            title = "",
            imageUrl = "",
            entryStatus = "",
            entryType = "",
            entryMalID = 2,
            userUID = "uid1"
        )
        val userFavorite3 = UserFavoritesEntity(
            id = 3,
            title = "",
            imageUrl = "",
            entryStatus = "",
            entryType = "",
            entryMalID = 3,
            userUID = "uid1"
        )
        val userFavorite4 = UserFavoritesEntity(
            id = 4,
            title = "",
            imageUrl = "",
            entryStatus = "",
            entryType = "",
            entryMalID = 4,
            userUID = "uid2"
        )
        dao.addToUserFavorites(userFavorite)
        dao.addToUserFavorites(userFavorite2)
        dao.addToUserFavorites(userFavorite3)
        dao.addToUserFavorites(userFavorite4)
        val returnedUserFavorites = dao.getAllNewest("", "uid1").first()
        assertThat(returnedUserFavorites).doesNotContain(userFavorite4)
        for (i in 0..returnedUserFavorites.size - 2) {
            assertThat(returnedUserFavorites[i].id).isGreaterThan(returnedUserFavorites[i+1].id)
        }
    }

    @Test
    fun getAllUserFavorites_UserFavoritesReturnedCorrectlyInCorrectOrder() = runTest {
        val userFavorite = UserFavoritesEntity(
            id = 1,
            title = "1",
            imageUrl = "",
            entryStatus = "planning",
            entryType = "",
            entryMalID = 1,
            userUID = "uid1"
        )
        val userFavorite2 = UserFavoritesEntity(
            id = 2,
            title = "2",
            imageUrl = "",
            entryStatus = "planning",
            entryType = "",
            entryMalID = 2,
            userUID = "uid1"
        )
        val userFavorite3 = UserFavoritesEntity(
            id = 3,
            title = "3",
            imageUrl = "",
            entryStatus = "finished",
            entryType = "",
            entryMalID = 3,
            userUID = "uid1"
        )
        val userFavorite4 = UserFavoritesEntity(
            id = 4,
            title = "4",
            imageUrl = "",
            entryStatus = "planning",
            entryType = "",
            entryMalID = 4,
            userUID = "uid1"
        )
        dao.addToUserFavorites(userFavorite)
        dao.addToUserFavorites(userFavorite2)
        dao.addToUserFavorites(userFavorite3)
        dao.addToUserFavorites(userFavorite4)
        val returnedUserFavoritesZA = dao.getAllByStatusZA("", "uid1", "planning").first()
        val returnedUserFavoritesAZ = dao.getAllByStatusAZ("", "uid1", "planning").first()
        val returnedUserFavoritesNewest = dao.getAllByStatusNewest("", "uid1", "planning").first()
        val returnedUserFavoritesOldest = dao.getAllByStatusOldest("", "uid1", "planning").first()
        for (i in 0..returnedUserFavoritesZA.size - 2) {
            assertThat(returnedUserFavoritesZA[i].title).isGreaterThan(returnedUserFavoritesZA[i+1].title)
        }
        for (i in 0..returnedUserFavoritesAZ.size - 2) {
            assertThat(returnedUserFavoritesAZ[i].title).isLessThan(returnedUserFavoritesAZ[i+1].title)
        }
        for (i in 0..returnedUserFavoritesNewest.size - 2) {
            assertThat(returnedUserFavoritesNewest[i].id).isGreaterThan(returnedUserFavoritesNewest[i+1].id)
        }
        for (i in 0..returnedUserFavoritesOldest.size - 2) {
            assertThat(returnedUserFavoritesOldest[i].id).isLessThan(returnedUserFavoritesOldest[i+1].id)
        }
    }
}