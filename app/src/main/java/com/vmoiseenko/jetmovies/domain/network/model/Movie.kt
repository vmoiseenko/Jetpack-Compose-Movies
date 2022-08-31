package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class Movie(
    var id: Int,
    var title: String,
    var overview: String,
    @SerializedName(value = "release_date")
    var releaseDate: String,
    @SerializedName(value = "poster_path")
    var posterPath: String,
    @SerializedName(value = "vote_average")
    var vote: Float,

) {
    fun imagePath(): String = "http://image.tmdb.org/t/p/w300${posterPath}"
}



