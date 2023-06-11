package com.example.randomanimegenerator.feature_details.presentation

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.randomanimegenerator.MainDispatcherRule
import com.example.randomanimegenerator.feature_details.data.repository.FakeDetailsRepository
import com.example.randomanimegenerator.feature_details.domain.use_cases.DetailsUseCases
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetCharactersUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetInfoUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetRecommendationsUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetReviewsUseCase
import com.example.randomanimegenerator.feature_details.domain.use_cases.GetStaffUseCase
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_library.presentation.LibraryStatus
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repo: FakeDetailsRepository
    private lateinit var viewModel: DetailsViewModel
    private val savedStateHandle = SavedStateHandle().apply {
        set("id", 0)
        set("type", "Anime")
    }
    private lateinit var detailsUseCases: DetailsUseCases

    @Before
    fun setUp() {
        repo = FakeDetailsRepository()
        detailsUseCases = DetailsUseCases(
            getInfoUseCase = GetInfoUseCase(repo),
            getCharactersUseCase = GetCharactersUseCase(repo),
            getRecommendationsUseCase = GetRecommendationsUseCase(repo),
            getReviewsUseCase = GetReviewsUseCase(repo),
            getStaffUseCase = GetStaffUseCase(repo)
        )
        viewModel = DetailsViewModel(detailsUseCases, repo, savedStateHandle)
    }

    @Test
    fun `Change library status, library status changed accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.SelectStatus(LibraryStatus.PAUSED))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.libraryStatus).isEqualTo(LibraryStatus.PAUSED)
        }
    }

    @Test
    fun `Expand synopsis, state updated accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.ExpandSynopsis)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.synopsisExpanded).isTrue()
        }
    }

    @Test
    fun `Popup cover image, state updated accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.PopUpImage)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.showPopUp).isTrue()
        }
    }

    @Test
    fun `Navigate back, ui event send accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.NavigateBack)
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(DetailsViewModel.UiEvent.NavigateBack)
        }
    }

    @Test
    fun `Add to favorites, entry added properly`() = runTest {
        val removeFromFavorites = false
        viewModel.onEvent(DetailsEvent.AddOrRemoveFromFavorites(removeFromFavorites))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.isFavorite).isTrue()
        }
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(DetailsViewModel.UiEvent.ShowSnackBar("Entry has been added to favorites"))
        }
    }

    @Test
    fun `Navigate to review, ui event send accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.NavigateToSingleReview("review", "someone", 0, "content"))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(DetailsViewModel.UiEvent.NavigateToDestination("review/someone/${0}/content"))
        }
    }

    @Test
    fun `Navigate to destination, ui event send accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.NavigateToDestination("destination"))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(DetailsViewModel.UiEvent.NavigateToDestination("destination/${0}/Anime"))
        }
    }

    @Test
    fun `Navigate to recommendation, ui event send accordingly`() = runTest {
        viewModel.onEvent(DetailsEvent.NavigateToRecommendation("recommendation", 1))
        viewModel.eventFlow.test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(DetailsViewModel.UiEvent.NavigateToDestination("recommendation/${1}/Anime"))
        }
    }

    @Test
    fun `Get recommendations and staff, staff and recommendations retrieved properly`() = runTest {
        viewModel.onEvent(DetailsEvent.GenerateRecommendationsAndStaff(Type.ANIME))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.getRecommendationsAndStaff).isFalse()
            for (i in 0..emission.recommendation.size - 2) {
                assertThat(emission.recommendation[i].malId).isEqualTo(0)
            }
            for (i in 0 until emission.staff.size) {
                assertThat(emission.staff[i].position).isEqualTo("0")
            }
        }
    }

    @Test
    fun `Test initialized data, main info retrieved properly`() = runTest {
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.malId).isEqualTo(0)
        }
    }

    @Test
    fun `Test initialized data, reviews retrieved properly`() = runTest {
        viewModel.state.test {
            val emission = awaitItem()
            for (i in 0 until emission.reviews.size) {
                assertThat(emission.reviews[i].score).isEqualTo(0)
                assertThat(emission.reviews[i].userName).isEqualTo("ANIME")
            }
        }
    }
    @Test
    fun `Test initialized data, characters retrieved properly`() = runTest {
        viewModel.state.test {
            val emission = awaitItem()
            for (i in 0 until emission.characters.size) {
                assertThat(emission.characters[i].name).isEqualTo("0")
            }
        }
    }

}