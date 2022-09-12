package com.vmoiseenko.jetmovies.domain.network.model

data class TVShows(
    var page: Int,
    var results: List<TVShow>
)
