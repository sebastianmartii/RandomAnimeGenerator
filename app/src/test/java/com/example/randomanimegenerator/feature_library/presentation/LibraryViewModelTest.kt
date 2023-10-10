package com.example.randomanimegenerator.feature_library.presentation

import app.cash.turbine.test
import com.example.randomanimegenerator.feature_details.presentation.AuthenticationClientTest
import com.example.randomanimegenerator.feature_library.data.repository.FakeLibraryRepository
import com.example.randomanimegenerator.feature_library.domain.use_case.GetAllByStatusUseCase
import com.example.randomanimegenerator.feature_library.domain.use_case.GetAllUseCase
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class LibraryViewModelTest {

    private lateinit var repo: FakeLibraryRepository
    private lateinit var viewModel: LibraryViewModel
    private lateinit var authenticationClient: AuthenticationClient
    @Before
    fun setUp() {
        repo = FakeLibraryRepository()
        authenticationClient = AuthenticationClientTest()
        viewModel = LibraryViewModel(GetAllUseCase(repo), GetAllByStatusUseCase(repo), authenticationClient)
    }

    @Test
    fun `Change search text, search text state updated accordingly`() = runTest {
        viewModel.onEvent(LibraryEvent.ChangeSearchText("custom query"))
        viewModel.searchText.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo("custom query")
        }
    }

    @Test
    fun `Change status, status changed accordingly`() = runTest {
        viewModel.onEvent(LibraryEvent.ChangeStatus(LibraryStatus.WATCHING))
        viewModel.libraryFilter.test {
            val emission = awaitItem()
            assertThat(emission.libraryStatus).isEqualTo(LibraryStatus.WATCHING)
        }
    }

    @Test
    fun `Change sort type, sort type changed accordingly`() = runTest {
        viewModel.onEvent(LibraryEvent.ChangeSortType(LibrarySortType.NEWEST))
        viewModel.libraryFilter.test {
            val emission = awaitItem()
            assertThat(emission.librarySortType).isEqualTo(LibrarySortType.NEWEST)
        }
    }

    @Test
    fun `Start searching, isSearching changed accordingly`() = runTest {
        viewModel.onEvent(LibraryEvent.Search)
        viewModel.isSearching.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(true)
        }
    }

    @Test
    fun `Clear text field, search text cleared`() = runTest {
        viewModel.onEvent(LibraryEvent.ClearTextField)
        viewModel.searchText.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo("")
        }
    }
}