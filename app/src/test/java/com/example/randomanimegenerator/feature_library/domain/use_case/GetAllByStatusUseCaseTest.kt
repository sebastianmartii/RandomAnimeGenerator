package com.example.randomanimegenerator.feature_library.domain.use_case

import com.example.randomanimegenerator.feature_details.data.mappers.toStatusString
import com.example.randomanimegenerator.feature_library.data.repository.FakeLibraryRepository
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetAllByStatusUseCaseTest {

    private lateinit var repo: FakeLibraryRepository

    @Before
    fun setUp() {
        repo = FakeLibraryRepository()
    }

    @Test
    fun `Order entries by id ascending with given library status, shows only ones with correct status and with correct order`(): Unit = runBlocking {
        val entries = repo.getAllByStatusAZ("", com.example.randomanimegenerator.feature_library.presentation.LibraryStatus.PLANNING.toStatusString()).first()

        for (i in 0 until entries.size - 2) {
            Truth.assertThat(entries[i].id).isLessThan(entries[i+1].id)
        }
    }

    @Test
    fun `Order entries by id descending with given library status, shows only ones with correct status and with correct order`(): Unit = runBlocking {
        val entries = repo.getAllByStatusZA("", com.example.randomanimegenerator.feature_library.presentation.LibraryStatus.FINISHED.toStatusString()).first()

        for (i in 0 until entries.size - 2) {
            Truth.assertThat(entries[i].id).isGreaterThan(entries[i+1].id)
        }
    }

    @Test
    fun `Order entries by title ascending with given library status, shows only ones with correct status and with correct order`(): Unit = runBlocking {
        val entries = repo.getAllByStatusNewest("", com.example.randomanimegenerator.feature_library.presentation.LibraryStatus.FINISHED.toStatusString()).first()

        for (i in 0 until entries.size - 2) {
            Truth.assertThat(entries[i].id).isLessThan(entries[i+1].id)
        }
    }

    @Test
    fun `Order entries by title descending with given library status, shows only ones with correct status and with correct order`(): Unit = runBlocking {
        val entries = repo.getAllByStatusOldest("", com.example.randomanimegenerator.feature_library.presentation.LibraryStatus.PLANNING.toStatusString()).first()

        for (i in 0 until entries.size - 2) {
            Truth.assertThat(entries[i].id).isGreaterThan(entries[i+1].id)
        }
    }
}