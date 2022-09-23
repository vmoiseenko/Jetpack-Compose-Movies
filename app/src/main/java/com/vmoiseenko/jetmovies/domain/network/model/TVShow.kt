package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName
import com.vmoiseenko.jetmovies.domain.network.model.Image.Companion.imagePath

data class TVShow(
    var id: Int,
    @SerializedName(value = "name")
    var title: String,
    var overview: String,
    @SerializedName(value = "first_air_date")
    var releaseDate: String,
    @SerializedName(value = "poster_path")
    var posterPath: String?,
    @SerializedName(value = "vote_average")
    var vote: Float

) {
    fun imagePath(): String = Poster.W342.imagePath(posterPath ?: "")
}



