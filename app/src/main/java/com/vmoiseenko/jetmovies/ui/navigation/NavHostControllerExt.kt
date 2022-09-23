package com.vmoiseenko.jetmovies.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavHostController.currentDestination(): String? {
    val currentBackStack by currentBackStackEntryAsState()
    return when (currentBackStack?.destination?.route) {
        Movies.route -> {
            val argument = checkNotNull(currentBackStack?.arguments?.getString(Movies.sourceType))
            Movies.getRouteWithArg(argument)
        }
        else -> currentBackStack?.destination?.route
    }
}

fun NavHostController.navigateSingleWithRestore(route: String) {
    val firstBottomBarDestination = backQueue
        .firstOrNull { route == it.destination.route }
        ?.destination
    // remove all navigation items from the stack
    // so only the currently selected screen remains in the stack
    if (firstBottomBarDestination != null) {
        this.popBackStack(firstBottomBarDestination.id, false)
    } else {
        this.navigate(route) {
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

fun NavHostController.navigateTo(route: String) =
    this.navigate(route) {
        restoreState = true
    }

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        launchSingleTop = true
        restoreState = true
    }

fun NavHostController.navigateToMovieDetails(
    movieId: Int
) {
    navigateSingleTopTo("${MovieDetails.route}/$movieId")
}

fun NavHostController.navigateToActorScreen(
    actorId: Int
) {
    navigateSingleTopTo("${ActorScreen.route}/$actorId")
}

fun NavHostController.navigateBack(
) {
    this.navigateUp()
}
