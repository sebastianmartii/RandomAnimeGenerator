package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.StaffDao
import com.example.randomanimegenerator.core.database.entities.StaffEntity
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
class StaffDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: RandomAnimeGeneratorDb
    private lateinit var dao: StaffDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.staffDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertStaff_staffInsertedProperly() = runTest {
        val staffMember = StaffEntity(
            id = 1,
            malId = 1,
            staffMemberImage = "",
            staffMemberName = "",
            staffMemberPositions = ""
        )
        val staffList = listOf(
            staffMember
        )
        dao.upsertStaff(staffList)
        val staff = dao.getStaff(1)
        assertThat(staff).contains(staffMember)
    }

    @Test
    fun deleteStaff_staffDeletedProperly() = runTest {
        val staffMember = StaffEntity(
            id = 1,
            malId = 1,
            staffMemberImage = "",
            staffMemberName = "",
            staffMemberPositions = ""
        )
        val staffList = listOf(
            staffMember
        )
        dao.upsertStaff(staffList)
        dao.deleteStaff(1)
        val staff = dao.getStaff(1)
        assertThat(staff).doesNotContain(staffMember)
    }

    @Test
    fun getStaffAsFlow_staffRetrievedProperly() = runTest {
        val staffMember = StaffEntity(
            id = 1,
            malId = 1,
            staffMemberImage = "",
            staffMemberName = "",
            staffMemberPositions = ""
        )
        val staffMember2 = StaffEntity(
            id = 2,
            malId = 1,
            staffMemberImage = "",
            staffMemberName = "",
            staffMemberPositions = ""
        )
        val staffList = listOf(
            staffMember,
            staffMember2
        )
        dao.upsertStaff(staffList)
        val flowOfStaff = dao.getStaffAsFlow(1).first()
        assertThat(flowOfStaff).contains(staffMember)
        assertThat(flowOfStaff).contains(staffMember2)
    }
}