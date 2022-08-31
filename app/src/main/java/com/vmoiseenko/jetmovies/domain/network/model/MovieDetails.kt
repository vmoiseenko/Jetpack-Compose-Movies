package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

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
    var backdropPath: String,
    @SerializedName(value = "vote_average")
    var vote: Float,

    ) {
    fun imagePath(): String = "http://image.tmdb.org/t/p/w780${backdropPath}"
}

data class Info(
    var id: Int,
    var name: String
)

