package com.example.randomanimegenerator.feature_generator.data.remote.anime_dto

import com.example.randomanimegenerator.feature_generator.domain.model.GeneratorModel

data class Data(
    val aired: Aired,
    val airing: Boolean,
    val approved: Boolean,
    val background: Any,
    val broadcast: Broadcast,
    val demographics: List<Demographic>,
    val duration: String,
    val episodes: Any,
    val explicit_genres: List<Any>,
    val favorites: Int,
    val genres: List<Genre>,
    val images: Images,
    val licensors: List<Any>,
    val mal_id: Int,
    val members: Int,
    val popularity: Int,
    val producers: List<Producer>,
    val rank: Any,
    val rating: String,
    val score: Any,
    val scored_by: Any,
    val season: String,
    val source: String,
    val status: String,
    val studios: List<Studio>,
    val synopsis: String,
    val themes: List<Theme>,
    val title: String,
    val title_english: String,
    val title_japanese: String,
    val title_synonyms: List<Any>,
    val titles: List<Title>,
    val trailer: Trailer,
    val type: String,
    val url: String,
    val year: Int
) {
    fun dataToGeneratorModel(): GeneratorModel {
        return GeneratorModel(
            title = title,
            imageUrl = images.jpg.image_url
        )
    }
}