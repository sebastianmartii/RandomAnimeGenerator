package com.example.randomanimegenerator.feature_details.data.mappers

import com.example.randomanimegenerator.core.database.entities.CharacterEntity
import com.example.randomanimegenerator.core.database.entities.MainInfoEntity
import com.example.randomanimegenerator.core.database.entities.RecommendationEntity
import com.example.randomanimegenerator.core.database.entities.ReviewEntity
import com.example.randomanimegenerator.core.database.entities.StaffEntity
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
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Genre
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Studio
import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Theme
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Author
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto.Data as AnimeCharactersData
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.staff_dto.Data as StaffData
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.manga_characters_dto.Data as MangaCharactersData
import com.example.randomanimegenerator.feature_details.data.remote.dto.recommendations_dto.Data as RecommendationData
import com.example.randomanimegenerator.feature_details.data.remote.dto.reviews_dto.Data as ReviewData
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Demographic as MangaDemographic
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Genre as MangaGenre
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.Theme as MangaTheme

fun AnimeDto.toMainInfoEntity(type: Type, isFavorite: Boolean): MainInfoEntity {
    return MainInfoEntity(
        malId = this.data.mal_id,
        title = this.data.title,
        imageUrl = this.data.images.jpg.image_url,
        synopsis = this.data.synopsis,
        type = this.data.type,
        status = this.data.status,
        score = this.data.score,
        genres = this.data.genres.toAnimeGenreModel().joinToString(separator = ", "),
        themes = this.data.themes.toAnimeThemeModel().joinToString(separator = ", "),
        demographic = this.data.demographics.toAnimeDemographicModel().joinToString(separator = ", "),
        mangaAuthors = "",
        mangaChapters = 0,
        source = this.data.source,
        episodes = this.data.episodes,
        studios = this.data.studios.toStudioModel().joinToString(separator = ", "),
        isFavorite = isFavorite,
        libraryType = type.toTypeString(),
        libraryStatus = "planned"
    )
}

fun MangaDto.toMainInfoEntity(type: Type, isFavorite: Boolean): MainInfoEntity {
    return MainInfoEntity(
        malId = this.data.mal_id,
        title = this.data.title,
        imageUrl = this.data.images.jpg.image_url,
        synopsis = this.data.synopsis,
        type = this.data.type,
        status = this.data.status,
        score = this.data.score,
        genres = this.data.genres.toMangaGenreModel().joinToString(separator = ", "),
        themes = this.data.themes.toMangaThemeModel().joinToString(separator = ", "),
        demographic = this.data.demographics.toMangaDemographicModel().joinToString(separator = ", "),
        mangaAuthors = this.data.authors.toAuthorModel().joinToString(separator = ", "),
        mangaChapters = this.data.chapters,
        source = "",
        episodes = 0,
        studios = "",
        isFavorite = isFavorite,
        libraryType = type.toTypeString(),
        libraryStatus = "planned"
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

fun MainInfoEntity?.toMainModel(): MainModel{
    return MainModel(
        authors = this?.mangaAuthors ?: "",
        chapters = this?.mangaChapters ?: 0,
        source = this?.source ?: "",
        episodes = this?.episodes ?: 0,
        studios = this?.studios ?: "",
        malId = this?.malId ?: 0,
        title = this?.title ?: "",
        imageUrl = this?.imageUrl ?: "",
        synopsis = this?.synopsis ?: "",
        type = this?.type ?: "",
        status = this?.status ?: "",
        score = this?.score ?: 0.0,
        genres = this?.genres ?: "",
        themes = this?.themes ?: "",
        demographic = this?.demographic ?: "",
        isLoading = false,
        isFavorite = this?.isFavorite ?: false
    )
}


// reviews mappers

fun ReviewData.toReviewEntity(malId: Int, type: Type): ReviewEntity {
    return ReviewEntity(
        malId = malId,
        type = type.toTypeString(),
        reviewAuthor = this.user.username,
        reviewScore = this.score,
        review = this.review
    )
}

fun ReviewsDto.toReviewsEntity(malId: Int, type: Type): List<ReviewEntity> {
    return this.data.map { it.toReviewEntity(malId, type) }
}

fun ReviewEntity.toReview(): Review {
    return Review(
        userName = this.reviewAuthor,
        score = this.reviewScore,
        review = this.review
    )
}

// recommendations mappers

fun RecommendationData.toRecommendationEntity(malId: Int, type: Type): RecommendationEntity {
    return RecommendationEntity(
        malId = malId,
        type = type.toTypeString(),
        recommendationImage = this.entry.images.jpg.image_url,
        recommendationTitle = this.entry.title
    )
}

fun RecommendationsDto.toRecommendationsEntity(malId: Int, type: Type): List<RecommendationEntity> {
    return this.data.map { it.toRecommendationEntity(malId, type) }
}

fun RecommendationEntity.toRecommendation(): Recommendation {
    return Recommendation(
        imageUrl = this.recommendationImage,
        title = this.recommendationTitle
    )
}

// characters mappers

fun AnimeCharactersData.toCharacterEntity(malId: Int, type: Type): CharacterEntity {
    return CharacterEntity(
        malId = malId,
        type = type.toTypeString(),
        characterRole = this.role,
        characterImage = this.character.images.jpg.image_url,
        characterName = this.character.name
    )
}

fun AnimeCharactersDto.toCharactersEntity(malId: Int, type: Type): List<CharacterEntity> {
    return this.data.map { it.toCharacterEntity(malId, type) }
}

fun MangaCharactersData.toCharacterEntity(malId: Int, type: Type): CharacterEntity {
    return CharacterEntity(
        malId = malId,
        type = type.toTypeString(),
        characterName = this.character.name,
        characterImage = this.character.images.jpg.image_url,
        characterRole = this.role
    )
}

fun MangaCharactersDto.toCharactersEntity(malId: Int, type: Type): List<CharacterEntity> {
    return this.data.map { it.toCharacterEntity(malId, type) }
}

fun CharacterEntity.toCharacter(): Character {
    return Character(
        imageUrl = this.characterImage,
        name = this.characterName,
        role = this.characterRole
    )
}

// staff mappers

fun StaffData.toStaffEntity(malId: Int): StaffEntity {
    return StaffEntity(
        malId = malId,
        staffMemberPositions = this.positions.joinToString(separator = ", "),
        staffMemberName = this.person.name,
        staffMemberImage = this.person.images.jpg.image_url
    )
}

fun StaffDto.toStaffEntity(malId: Int): List<StaffEntity> {
    return this.data.map { it.toStaffEntity(malId) }
}

fun StaffEntity.toStaff(): Staff {
    return Staff(
        name = this.staffMemberName,
        imageUrl = this.staffMemberImage,
        position = this.staffMemberPositions
    )
}

