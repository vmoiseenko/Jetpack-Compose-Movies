package com.vmoiseenko.jetmovies.domain.dto

import com.vmoiseenko.jetmovies.domain.network.model.Movie
import com.vmoiseenko.jetmovies.domain.network.model.TVShow
import java.time.LocalDate

data class MovieItem(
    val id: Int,
    val title: String,
    val year: String?,
    val rating: Float,
    val imageUrl: String
)

fun Movie.mapToItem(): MovieItem {
    return MovieItem(
        id = this.id,
        title = this.title,
        year = this.releaseDate.takeIf { it.isNotBlank() }
            ?.let { LocalDate.parse(it).year.toString() },
        rating = this.vote,
        imageUrl = this.imagePath()
    )
}

fun TVShow.mapToItem(): MovieItem {
    return MovieItem(
        id = this.id,
        title = this.title,
        year = this.releaseDate.takeIf { it.isNotBlank() }
            ?.let { LocalDate.parse(it).year.toString() },
        rating = this.vote,
        imageUrl = this.imagePath()
    )
}