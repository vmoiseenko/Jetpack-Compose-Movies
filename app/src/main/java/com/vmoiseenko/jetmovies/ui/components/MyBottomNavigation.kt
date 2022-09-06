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
import com.vmoiseenko.jetmovies.ui.navigation.*
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryLightColor
import com.vmoiseenko.jetmovies.ui.theme.MovieSecondaryLighterColor

@Composable
fun MyBottomNavigation(
    currentDestination: String,
    onItemClick: (destination: BottomBarDestination) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MoviePrimaryLightColor
    ) {

        MoviesTabs.values().forEach { tab ->
            val isSelected = tab.destination.route == currentDestination
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
                    onItemClick(tab.destination)
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
    val destination: BottomBarDestination
) {
    MOVIES(R.string.placeholder_movies, Icons.Outlined.Movie, Icons.Filled.Movie, Movies),
    TV_SHOWS(R.string.placeholder_tv_shows, Icons.Outlined.Tv, Icons.Filled.Tv, TvShows),
    FAVORITES(
        R.string.placeholder_favorites,
        Icons.Outlined.FavoriteBorder,
        Icons.Filled.Favorite,
        Favorites
    ),
    SEARCH(
        R.string.placeholder_account,
        Icons.Outlined.AccountCircle,
        Icons.Filled.AccountCircle,
        Account
    );

    companion object {
        fun hasTab(route: String): Boolean {
            return values().map { it.destination.route }.contains(route)
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
