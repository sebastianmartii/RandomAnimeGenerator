package com.example.randomanimegenerator.feature_details.data.remote

import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.AnimeDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.anime_characters_dto.AnimeCharactersDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.anime_dtos.staff_dto.StaffDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.MangaDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.manga_dtos.manga_characters_dto.MangaCharactersDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.recommendations_dto.RecommendationsDto
import com.example.randomanimegenerator.feature_details.data.remote.dto.reviews_dto.ReviewsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DetailsApi {

    @GET("/v4/anime/{id}")
    suspend fun getAnime( @Path("id") id: Int): AnimeDto

    @GET("/v4/manga/{id}")
    suspend fun getManga( @Path("id") id: Int): MangaDto

    @GET("/v4/anime/{id}/reviews")
    suspend fun getAnimeReviews( @Path("id") id: Int): ReviewsDto

    @GET("/v4/manga/{id}/reviews")
    suspend fun getMangaReviews( @Path("id") id: Int): ReviewsDto

    @GET("/v4/anime/{id}/characters")
    suspend fun getAnimeCharacters( @Path("id") id: Int): AnimeCharactersDto

    @GET("/v4/manga/{id}/characters")
    suspend fun getMangaCharacters( @Path("id") id: Int): MangaCharactersDto

    @GET("/v4/anime/{id}/staff")
    suspend fun getAnimeStaff( @Path("id") id: Int): StaffDto

    @GET("/v4/anime/{id}/recommendations")
    suspend fun getAnimeRecommendations( @Path("id") id: Int): RecommendationsDto

    @GET("/v4/manga/{id}/recommendations")
    suspend fun getMangaRecommendations( @Path("id") id: Int): RecommendationsDto

}