package com.example.randomanimegenerator.feature_generator.domain.use_case

import com.example.randomanimegenerator.feature_generator.data.repository.FakeGeneratorRepository
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GenerateContentUseCaseTest {

    private lateinit var repo: FakeGeneratorRepository

    @Before
    fun setUp() {
        repo = FakeGeneratorRepository()
    }

    @Test
    fun `Get random Anime, content retrieved with correct type`() = runTest {
        val anime = repo.generateAnime(0,0).first()
        for (i in 0 until anime.data!!.size) {
            assertThat(anime.data!![i].libraryType).isEqualTo("Anime")
        }
    }

    @Test
    fun `Get random Manga, content retrieved with correct type`() = runTest {
        val manga = repo.generateManga(0,0).first()
        for (i in 0 until manga.data!!.size) {
            assertThat(manga.data!![i].libraryType).isEqualTo("Manga")
        }
    }
}