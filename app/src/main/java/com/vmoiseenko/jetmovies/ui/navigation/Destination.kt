package com.vmoiseenko.jetmovies.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

interface BottomBarDestination : Destination {}

object Movies : BottomBarDestination {
    override val route = "movies"
}

object TvShows : BottomBarDestination {
    override val route = "tv_shows"
}

object Favorites : BottomBarDestination {
    override val route = "favorites"
}

object Account : BottomBarDestination {
    override val route = "account"
}

object MovieDetails : Destination {
    override val route = "movieDetails"
    const val movieId = "movie_id"
    val routeWithArgs = "${route}/{${movieId}}"
    val arguments = listOf(
        navArgument(movieId) { type = NavType.IntType }
    )
}
