package com.example.randomanimegenerator.feature_generator.data.remote.manga_dto

import com.example.randomanimegenerator.feature_generator.data.remote.anime_dto.Demographic

data class MangaData(
    val approved: Boolean,
    val authors: List<Author>,
    val background: Any,
    val chapters: Int,
    val demographics: List<Demographic>,
    val explicit_genres: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val published: Published,
    val publishing: Boolean,
    val rank: Int,
    val score: Double,
    val scored: Double,
    val scored_by: Int,
    val serializations: List<Any>,
    val status: String,
    val synopsis: String,
    val themes: List<Any>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<Any>,
    val titles: List<Title>,
    val type: String,
    val url: String,
    val volumes: Int
)