package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class MovieCredits(
    val id: Int,
    val cast: List<Cast>,
    val crew: List<Crew>,
)

data class Cast(
    val name: String,
    @SerializedName(value = "profile_path")
    val profileImage: String,
    val character: String
) {

    fun imagePath(): String = "http://image.tmdb.org/t/p/w185${profileImage}"
}

data class Crew(
    val name: String,
    val job: String
)

const val DIRECTOR = "Director"
const val WRITER = "Writer"
const val SCREENPLAY = "Screenplay"
