package com.vmoiseenko.jetmovies.ui.screens.movies

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.ui.components.ErrorItem
import com.vmoiseenko.jetmovies.ui.components.MoviesGridList
import com.vmoiseenko.jetmovies.ui.components.MoviesGridPaging
import com.vmoiseenko.jetmovies.ui.components.SearchBar
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor

@Composable
fun MoviesScreen(
    onMovieClick: (Int) -> Unit = {},
    viewModel: MoviesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MoviePrimaryBackgroundColor)
    ) {

        val viewState by viewModel.uiState.collectAsState()
        val pagingItems: LazyPagingItems<MovieItem> =
            viewModel.pagingMoviesFlow.collectAsLazyPagingItems()

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
            is MoviesContract.UiState.Paging -> {
                MoviesGridPaging(
                    items = pagingItems,
                    onMovieClick = { movieId -> onMovieClick(movieId) }
                )
            }
            is MoviesContract.UiState.Error -> {
                ErrorItem(
                    message = (viewState as MoviesContract.UiState.Error).message,
                    onRetryClick = { viewModel.retry() }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewWellnessScreen() {
    JetMoviesTheme {
        Surface {
//            MoviesScreen()
        }
    }
}
