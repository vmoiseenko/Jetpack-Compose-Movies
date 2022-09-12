package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName
import com.vmoiseenko.jetmovies.domain.network.model.Image.Companion.imagePath

data class MovieDetails(
    var id: Int,
    var title: String,
    var overview: String,
    val genres: List<Info>,
    val runtime: Int,
    @SerializedName(value = "production_companies")
    var production: List<Info>,
    @SerializedName(value = "release_date")
    var releaseDate: String,
    @SerializedName(value = "backdrop_path")
    var backdropPath: String?,
    @SerializedName(value = "poster_path")
    var posterPath: String,
    @SerializedName(value = "vote_average")
    var vote: Float
) {

    fun imagePath() = backdropPath?.let {
        Backdrop.W780.imagePath(it)
    } ?: Poster.W780.imagePath(posterPath)
}

data class Info(
    var id: Int,
    var name: String
)

