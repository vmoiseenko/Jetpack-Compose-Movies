package com.vmoiseenko.jetmovies.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Destination {
    val route: String
}

interface BottomBarDestination : Destination

object Movies : BottomBarDestination {
    const val id = "movies"
    const val sourceType = "source_type"
    override val route = "${id}/{${sourceType}}"

    val arguments = listOf(
        navArgument(sourceType) {
            type = NavType.StringType;
            defaultValue = SourceType.MOVIE.type
        }
    )

    enum class SourceType(val type: String) {
        MOVIE("movie"),
        TV_SHOW("tv_shows")
    }

    fun getRouteWithArg(sourceType: SourceType): String {
        return "${id}/${sourceType.type}"
    }

    fun getRouteWithArg(value: String): String {
        return "${id}/${value}"
    }
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

object ActorScreen : Destination {
    override val route = "actorScreen"
    const val actorId = "actor_id"
    val routeWithArgs = "${route}/{${actorId}}"
    val arguments = listOf(
        navArgument(actorId) { type = NavType.IntType }
    )
}
