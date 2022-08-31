package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryLightColor


@Composable
fun MyBottomNavigation(
    onHomeClick: () -> Unit,
    onFavoritesClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier,
        backgroundColor = MoviePrimaryLightColor
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Spa,
                    tint = Color.White,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.placeholder_home),
                    color = Color.White
                )
            },
            selected = true,
            onClick = { onHomeClick() }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    tint = Color.White,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(id = R.string.placeholder_favorites),
                    color = Color.White
                )
            },
            selected = true,
            onClick = { onFavoritesClick() }
        )
    }
}

@Preview
@Composable
fun PreviewMyBottomNavigation() {
    JetMoviesTheme() {
        Surface() {
            MyBottomNavigation({}, {})
        }
    }
}
