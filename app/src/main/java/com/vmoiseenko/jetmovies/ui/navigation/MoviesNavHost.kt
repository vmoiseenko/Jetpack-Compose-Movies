package com.vmoiseenko.jetmovies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vmoiseenko.jetmovies.ui.screens.account.AccountScreen
import com.vmoiseenko.jetmovies.ui.screens.artist.ActorScreen
import com.vmoiseenko.jetmovies.ui.screens.details.MovieDetailsScreen
import com.vmoiseenko.jetmovies.ui.screens.favorites.FavoritesScreen
import com.vmoiseenko.jetmovies.ui.screens.movies.MoviesScreen
import com.vmoiseenko.jetmovies.ui.screens.tv.TvShowsScreen

@Composable
fun MoviesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Movies.route,
        modifier = modifier
    ) {
        composable(route = Movies.route) {
            MoviesScreen(
                onMovieClick = {
                    navController.navigateToMovieDetails(it)
                }
            )
        }
        composable(route = TvShows.route) {
            TvShowsScreen()
        }
        composable(route = Favorites.route) {
            FavoritesScreen()
        }
        composable(route = Account.route) {
            AccountScreen()
        }
        composable(
            route = MovieDetails.routeWithArgs,
            arguments = MovieDetails.arguments
        ) { navBackStackEntry ->
            val movieId =
                navBackStackEntry.arguments?.getInt(MovieDetails.movieId) ?: 0
            MovieDetailsScreen(movieId, {
                navController.navigateToActorScreen(it)
            })
        }
        composable(
            route = ActorScreen.routeWithArgs,
            arguments = ActorScreen.arguments
        ) { navBackStackEntry ->
            val artistId =
                navBackStackEntry.arguments?.getInt(ActorScreen.actorId) ?: 0
            ActorScreen(artistId, navController::navigateBack)
        }
    }
}
