package com.vmoiseenko.jetmovies.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

object Movies: Destination {
    override val route = "movies"
}

object MovieDetails: Destination {
    override val route = "movieDetails"
    const val movieId = "movie_id"
    val routeWithArgs = "${route}/{${movieId}}"
    val arguments = listOf(
        navArgument(movieId) { type = NavType.IntType }
    )
}
