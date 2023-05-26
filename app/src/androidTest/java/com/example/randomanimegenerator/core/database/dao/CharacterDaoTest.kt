package com.example.randomanimegenerator.core.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.randomanimegenerator.core.database.RandomAnimeGeneratorDb
import com.example.randomanimegenerator.core.database.daos.CharacterDao
import com.example.randomanimegenerator.core.database.entities.CharacterEntity
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
class CharacterDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("test_db")
    lateinit var db: RandomAnimeGeneratorDb
    private lateinit var dao: CharacterDao

    @Before
    fun setUp() {
        hiltRule.inject()
        dao = db.characterDao
    }

    @After
    fun teardown() {
        db.close()
    }

    @Test
    fun insertCharacter_characterInsertedProperly() = runTest {
        val character = CharacterEntity(
            id = 1,
            malId = 1,
            type = "",
            characterName = "",
            characterImage = "",
            characterRole = ""
        )
        val characterList = listOf(
            character
        )
        dao.upsertCharacters(characterList)
        val characters = dao.getCharacters(1, "")
        assertThat(characters).contains(character)
    }

    @Test
    fun deleteCharacter_characterDeletedProperly() = runTest {
        val character = CharacterEntity(
            id = 1,
            malId = 1,
            type = "",
            characterName = "",
            characterImage = "",
            characterRole = ""
        )
        val characterList = listOf(
            character
        )
        dao.upsertCharacters(characterList)
        dao.deleteCharacters(1, "")
        val characters = dao.getCharacters(1, "")
        assertThat(characters).doesNotContain(character)
    }

    @Test
    fun getCharactersAsFlow_charactersProperlyRetrieved() = runTest {
        val character = CharacterEntity(
            id = 1,
            malId = 1,
            type = "",
            characterName = "",
            characterImage = "",
            characterRole = ""
        )
        val character2 = CharacterEntity(
            id = 2,
            malId = 1,
            type = "",
            characterName = "",
            characterImage = "",
            characterRole = ""
        )
        val characterList = listOf(
            character,
            character2
        )
        dao.upsertCharacters(characterList)
        val flowOfCharacters = dao.getCharactersAsFlow(1, "").first()
        assertThat(flowOfCharacters).contains(character)
        assertThat(flowOfCharacters).contains(character2)
        val character3 = CharacterEntity(
            id = 3,
            malId = 1,
            type = "",
            characterName = "",
            characterImage = "",
            characterRole = ""
        )
        val characterList2 = listOf(
            character,
            character2,
            character3
        )
        dao.upsertCharacters(characterList2)
        val flowOfCharacters2 = dao.getCharactersAsFlow(1, "").first()
        assertThat(flowOfCharacters2).contains(character3)
    }
}