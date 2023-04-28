package com.example.randomanimegenerator.feature_details.data.mappers

import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.AnimeDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto.AnimeCharactersDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.staff_dto.StaffDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.MangaDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.manga_characters_dto.MangaCharactersDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.recommendations_dto.RecommendationsDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.reviews_dto.ReviewsDto
import com.example.randomanimegenerator.feature_details.domain.model.Character
import com.example.randomanimegenerator.feature_details.domain.model.MainModel
import com.example.randomanimegenerator.feature_details.domain.model.Recommendation
import com.example.randomanimegenerator.feature_details.domain.model.Review
import com.example.randomanimegenerator.feature_details.domain.model.Staff
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Demographic
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Studio
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Author
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Genre
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Theme
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto.Data as AnimeCharactersData
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.staff_dto.Data as StaffData
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.manga_characters_dto.Data as MangaCharactersData
import com.example.randomanimegenerator.feature_details.data.remote.dto.recommendations_dto.Data as RecommendationData
import com.example.randomanimegenerator.feature_details.data.remote.dto.reviews_dto.Data as ReviewData
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Demographic as MangaDemographic
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Genre as MangaGenre
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Theme as MangaTheme

fun AnimeDto.toMainModel(): MainModel {
    return MainModel(
        malId = this.data.mal_id,
        title = this.data.title,
        imageUrl = this.data.images.jpg.image_url,
        synopsis = this.data.synopsis,
        type = this.data.type,
        status = this.data.status,
        source = this.data.source,
        episodes = this.data.episodes,
        score = this.data.score,
        studios = this.data.studios.toStudioModel(),
        genres = this.data.genres.toAnimeGenreModel(),
        themes = this.data.themes.toAnimeThemeModel(),
        demographic = this.data.demographics.toAnimeDemographicModel(),
        // manga only parameters
        chapters = 0,
        authors = emptyList(),
        isLoading = false
    )
}

fun MangaDto.toMainModel(): MainModel {
    return MainModel(
        malId = this.data.mal_id,
        title = this.data.title,
        imageUrl = this.data.images.jpg.image_url,
        synopsis = this.data.synopsis,
        type = this.data.type,
        status = this.data.status,
        chapters = this.data.chapters,
        authors = this.data.authors.toAuthorModel(),
        score = this.data.score,
        genres = this.data.genres.toMangaGenreModel(),
        themes = this.data.themes.toMangaThemeModel(),
        demographic = this.data.demographics.toMangaDemographicModel(),
        // anime only parameters
        source = "",
        episodes = 0,
        studios = emptyList(),
        isLoading = false
    )
}

fun List<Studio>.toStudioModel(): List<String> {
    return this.map { it.name }
}

fun List<Genre>.toAnimeGenreModel(): List<String> {
    return this.map { it.name }
}

fun List<Theme>.toAnimeThemeModel(): List<String> {
    return this.map { it.name }
}

fun List<Demographic>.toAnimeDemographicModel(): List<String> {
    return this.map { it.name }
}

fun List<MangaGenre>.toMangaGenreModel(): List<String> {
    return this.map { it.name }
}

fun List<MangaTheme>.toMangaThemeModel(): List<String> {
    return this.map { it.name }
}

fun List<MangaDemographic>.toMangaDemographicModel(): List<String> {
    return this.map { it.name }
}

fun List<Author>.toAuthorModel(): List<String> {
    return this.map { it.name }
}


// reviews mappers

fun ReviewData.toReview(): Review {
    return Review(
        userName = this.user.username,
        score = this.score,
        review = this.review
    )
}

fun ReviewsDto.toReviews(): List<Review> {
    return this.data.map {
        it.toReview()
    }
}

// recommendations mappers

fun RecommendationData.toRecommendation(): Recommendation {
    return Recommendation(
        imageUrl = this.entry.images.jpg.image_url,
        title = this.entry.title
    )
}

fun RecommendationsDto.toRecommendations(): List<Recommendation> {
    return this.data.map { it.toRecommendation() }
}

// characters mappers

fun AnimeCharactersData.toCharacter(): Character {
    return Character(
        imageUrl = this.character.images.jpg.image_url,
        name = this.character.name,
        role = this.role
    )
}

fun AnimeCharactersDto.toCharacters(): List<Character> {
    return this.data.map { it.toCharacter() }
}

fun MangaCharactersData.toCharacter(): Character {
    return Character(
        imageUrl = this.character.images.jpg.image_url,
        name = this.character.name,
        role = this.role
    )
}

fun MangaCharactersDto.toCharacters(): List<Character> {
    return this.data.map { it.toCharacter() }
}

// staff mappers

fun StaffData.toStaffMember(): Staff {
    return Staff(
        name = this.person.name,
        imageUrl = this.person.images.jpg.image_url,
        position = this.positions
    )
}

fun StaffDto.toStaff(): List<Staff> {
    return this.data.map { it.toStaffMember() }
}

