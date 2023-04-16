package com.example.randomanimegenerator.feature_generator.data.remote

import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.AnimeListDto
import com.example.randomanimegenerator.feature_generator.data.remote.manga_dto.MangaListDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeneratorApi {

    @GET("/v4/anime")
    suspend fun getAllAnime(
        @Query("min_score") minScore: Int,
        @Query("page") page: Int,
    ): Response<AnimeListDto>

    @GET("/v4/manga")
    suspend fun getAllManga(
        @Query("min_score") minScore: Int,
        @Query("page") page: Int,
    ): Response<MangaListDto>
}