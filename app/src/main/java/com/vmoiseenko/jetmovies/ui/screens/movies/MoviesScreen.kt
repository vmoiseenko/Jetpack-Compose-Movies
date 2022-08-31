package com.vmoiseenko.jetmovies.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.ui.components.SearchBar
import com.vmoiseenko.jetmovies.ui.components.MoviesGridList
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun MoviesScreen(
    onMovieClick: (Int) -> Unit = {},
    viewModel: MoviesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.background(MoviePrimaryBackgroundColor)

    ) {
        val viewState by viewModel.uiState.collectAsState()

        SearchBar(onValueChange = {
            viewModel.search(it)
        })

        when (viewState) {
            MoviesContract.UiState.Loading -> {
                Box(modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .padding(top = 24.dp)
                    )
                }
            }
            is MoviesContract.UiState.Movies -> {
                MoviesGridList(
                    list = (viewState as MoviesContract.UiState.Movies).movies,
                    onMovieClick = { movieId -> onMovieClick(movieId) }
                )
            }
            is MoviesContract.UiState.Error -> {
                Column() {
                    Text(text = (viewState as MoviesContract.UiState.Error).message)
                    Button(onClick = { viewModel.retry() }) {
                        Text(text = "Retry")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewWellnessScreen() {
    JetMoviesTheme() {
        Surface {
//            MoviesScreen()
        }
    }
}
