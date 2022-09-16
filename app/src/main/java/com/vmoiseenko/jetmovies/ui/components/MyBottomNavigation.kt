package com.vmoiseenko.jetmovies.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Movie
import androidx.compose.material.icons.outlined.Tv
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.navigation.Account
import com.vmoiseenko.jetmovies.ui.navigation.Favorites
import com.vmoiseenko.jetmovies.ui.navigation.Movies
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryLightColor
import com.vmoiseenko.jetmovies.ui.theme.MovieSecondaryLighterColor

@Composable
fun MyBottomNavigation(
    currentDestination: String,
    onItemClick: (destination: String) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MoviePrimaryLightColor
    ) {

        MoviesTabs.values().forEach { tab ->
            val isSelected = tab.route == currentDestination
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected) {
                            tab.selectedIcon
                        } else {
                            tab.icon
                        },
                        tint = getColor(isSelected),
                        contentDescription = "tab icon"
                    )
                },
                label = {
                    Text(
                        text = stringResource(id = tab.title),
                        color = getColor(isSelected)
                    )
                },
                selected = isSelected,
                onClick = {
                    onItemClick(tab.route)
                }
            )
        }
    }
}

private fun getColor(isSelected: Boolean): Color = if (isSelected) {
    Color.White
} else {
    MovieSecondaryLighterColor
}

enum class MoviesTabs(
    @StringRes val title: Int,
    val icon: ImageVector,
    val selectedIcon: ImageVector,
    val route: String
) {
    MOVIES(
        R.string.placeholder_movies,
        Icons.Outlined.Movie,
        Icons.Filled.Movie,
        Movies.getRouteWithArg(
            Movies.SourceType.MOVIE
        )
    ),
    TV_SHOWS(
        R.string.placeholder_tv_shows, Icons.Outlined.Tv, Icons.Filled.Tv, Movies.getRouteWithArg(
            Movies.SourceType.TV_SHOW
        )
    ),
    FAVORITES(
        R.string.placeholder_favorites,
        Icons.Outlined.FavoriteBorder,
        Icons.Filled.Favorite,
        Favorites.route
    ),
    SEARCH(
        R.string.placeholder_account,
        Icons.Outlined.AccountCircle,
        Icons.Filled.AccountCircle,
        Account.route
    );

    companion object {
        fun hasTab(route: String): Boolean {
            return values().map { it.route }.contains(route)
        }
    }

}

@Preview
@Composable
fun PreviewMyBottomNavigation() {
    JetMoviesTheme {
        Surface {
            MyBottomNavigation(Movies.route, {})
        }
    }
}
