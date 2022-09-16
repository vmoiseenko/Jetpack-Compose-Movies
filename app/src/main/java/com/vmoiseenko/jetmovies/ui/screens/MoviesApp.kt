package com.vmoiseenko.jetmovies.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.vmoiseenko.jetmovies.ui.components.MyBottomNavigation
import com.vmoiseenko.jetmovies.ui.navigation.Movies
import com.vmoiseenko.jetmovies.ui.navigation.MoviesNavHost
import com.vmoiseenko.jetmovies.ui.navigation.currentDestination
import com.vmoiseenko.jetmovies.ui.navigation.navigateSingleTopTo
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun MoviesApp() {
    JetMoviesTheme {
        val navController = rememberNavController()
        val initialScreen = Movies
        val currentDestination = navController.currentDestination() ?: initialScreen.route

        Scaffold(
            bottomBar = {
//                if (MoviesTabs.hasTab(currentDestination)) {
                    MyBottomNavigation(
                        currentDestination,
                        navController::navigateSingleTopTo
                    )
//                }
            }
        ) { innerPadding ->
            MoviesNavHost(
                navController = navController,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}
