package com.vmoiseenko.jetmovies.domain.network.model

data class Movies(
    var page: Int,
    var results: List<Movie>
)
