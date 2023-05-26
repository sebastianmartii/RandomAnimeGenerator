package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.MainInfoDao
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        dao.insert(mainInfo)
        dao.delete(1, "")
        val info = dao.getAll("").first()
        assertThat(info).doesNotContain(mainInfo)
    }

    @Test
    fun updateIsFavorite_entryAddedOrRemovedProperly() = runTest {
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        dao.insert(mainInfo)
        dao.updateEntry(1, "", true)
        val info = dao.getOne(1, "")
        assertThat(info.isFavorite).isEqualTo(true)
    }

    @Test
    fun updateLibraryStatus_statusUpdatedProperly() = runTest {
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        dao.insert(mainInfo)
        dao.updateEntryStatus(1, "", LibraryStatus.FINISHED.toStatusString())
        val info = dao.getOne(1, "")
        assertThat(info.libraryStatus).contains(LibraryStatus.FINISHED.toStatusString())
    }

    @Test
    fun getEntriesByStatusSorted_entriesRetrievedProperlyWithProperOrder() = runTest {
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
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        )
        val mainInfo2 = MainInfoEntity(
            id = 2,
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        val mainInfo3 = MainInfoEntity(
            id = 3,
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
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        )
        val mainInfo4 = MainInfoEntity(
            id = 4,
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        dao.insert(mainInfo)
        dao.insert(mainInfo2)
        dao.insert(mainInfo3)
        dao.insert(mainInfo4)
        val infoFinished = dao.getAllByStatusAZ("", LibraryStatus.FINISHED.toStatusString()).first()
        for (i in 0..infoFinished.size - 2) {
            assertThat(infoFinished[i].libraryStatus).isEqualTo(LibraryStatus.FINISHED.toStatusString())
            assertThat(infoFinished[i].title).isLessThan(infoFinished[i+1].title)
        }
        val infoPlanning = dao.getAllByStatusZA("", LibraryStatus.PLANNING.toStatusString()).first()
        for (i in 0..infoPlanning.size - 2) {
            assertThat(infoPlanning[i].libraryStatus).isEqualTo(LibraryStatus.PLANNING.toStatusString())
            assertThat(infoPlanning[i].title).isGreaterThan(infoPlanning[i+1].title)
        }
    }

    @Test
    fun getEntriesSorted_entriesRetrievedWithProperOrder() = runTest {
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
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        )
        val mainInfo2 = MainInfoEntity(
            id = 2,
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        val mainInfo3 = MainInfoEntity(
            id = 3,
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
            libraryStatus = LibraryStatus.FINISHED.toStatusString()
        )
        val mainInfo4 = MainInfoEntity(
            id = 4,
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
            libraryStatus = LibraryStatus.PLANNING.toStatusString()
        )
        dao.insert(mainInfo)
        dao.insert(mainInfo2)
        dao.insert(mainInfo3)
        dao.insert(mainInfo4)
        val infoNewest = dao.getAllNewest("").first()
        for (i in 0..infoNewest.size - 2) {
            assertThat(infoNewest[i].id).isLessThan(infoNewest[i+1].id)
        }
        val infoOldest = dao.getAll("").first()
        for (i in 0..infoOldest.size - 2) {
            assertThat(infoOldest[i].id).isGreaterThan(infoOldest[i+1].id)
        }
    }
}