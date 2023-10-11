package com.example.randomanimegenerator.feature_generator.presentation

import app.cash.turbine.test
import com.example.randomanimegenerator.feature_generator.data.repository.FakeGeneratorRepository
import com.example.randomanimegenerator.feature_generator.domain.use_case.GenerateContentUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GeneratorViewModelTest {


    private lateinit var viewModel: GeneratorViewModel
    private lateinit var repo: FakeGeneratorRepository

    @Before
    fun setUp() {
        repo = FakeGeneratorRepository()
        viewModel = GeneratorViewModel(GenerateContentUseCase(repo))
    }

    @Test
    fun `Edit generator parameters, state updated accordingly`() = runTest {
        viewModel.onEvent(GeneratorEvent.EditGeneratorParams)
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.editGeneratingParams).isEqualTo(true)
        }
    }

    @Test
    fun `Set amount, state updated accordingly`() = runTest {
        viewModel.onEvent(GeneratorEvent.SetAmount(Amount.TWENTY_FIVE))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.amountSelected).isEqualTo(Amount.TWENTY_FIVE)
        }
    }

    @Test
    fun `Set score, state updated accordingly`() = runTest {
        viewModel.onEvent(GeneratorEvent.SetScore("7"))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.scoreSelected).isEqualTo("7")
        }
    }

    @Test
    fun `Set type, state updated accordingly`() = runTest {
        viewModel.onEvent(GeneratorEvent.SetType(Type.MANGA))
        viewModel.state.test {
            val emission = awaitItem()
            assertThat(emission.typeSelected).isEqualTo(Type.MANGA)
        }
    }
}