package com.vmoiseenko.jetmovies.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vmoiseenko.jetmovies.ui.components.MyBottomNavigation
import com.vmoiseenko.jetmovies.ui.navigation.Favorites
import com.vmoiseenko.jetmovies.ui.navigation.MovieDetails
import com.vmoiseenko.jetmovies.ui.navigation.Movies
import com.vmoiseenko.jetmovies.ui.screens.details.MovieDetailsScreen
import com.vmoiseenko.jetmovies.ui.screens.favorites.FavoritesScreen
import com.vmoiseenko.jetmovies.ui.screens.movies.MoviesScreen
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun MoviesApp() {
    JetMoviesTheme() {
        val navController = rememberNavController()

        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = Movies

        Scaffold(
            bottomBar = {
                MyBottomNavigation(
                    { navController.navigateSingleTopTo(Movies.route) },
                    { navController.navigateSingleTopTo(Favorites.route) }
                )
            }
        ) { innerPadding ->
            MoviesNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

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
        composable(route = Favorites.route) {
            FavoritesScreen()
        }
        composable(
            route = MovieDetails.routeWithArgs,
            arguments = MovieDetails.arguments
        ) { navBackStackEntry ->
            val movieId =
                navBackStackEntry.arguments?.getInt(MovieDetails.movieId) ?: 0
            MovieDetailsScreen(movieId)
        }
    }
}

fun NavHostController.navigateToMovieDetails(
    movieId: Int
) {
    navigateSingleTopTo("${MovieDetails.route}/$movieId")
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) { launchSingleTop = true }
