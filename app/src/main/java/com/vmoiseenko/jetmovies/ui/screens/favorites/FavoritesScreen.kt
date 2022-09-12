package com.vmoiseenko.jetmovies.ui.screens.favorites

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.components.EmptyState
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun FavoritesScreen() {
    EmptyState(
        title = R.string.favorite_empty,
        image = R.drawable.onboarding
    )
}

@Preview
@Composable
fun PreviewFavorites() {
    JetMoviesTheme {
        Surface {
            FavoritesScreen()
        }
    }
}
