package com.vmoiseenko.jetmovies.ui.screens.tv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.domain.network.model.TVShowDetails
import com.vmoiseenko.jetmovies.ui.components.LoadingState
import com.vmoiseenko.jetmovies.ui.screens.tv.TvShowDetailsContract.UiState.Loading
import com.vmoiseenko.jetmovies.ui.screens.tv.TvShowDetailsContract.UiState.Success
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun TvShowDetailsScreen(
    viewModel: TvShowDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val viewState by viewModel.uiState.collectAsState()

    when (viewState) {
        is Success -> TvShowDetailsUI((viewState as Success).details, modifier)
        is Loading -> LoadingState(modifier)
        else -> Unit
    }
}

@Composable
fun TvShowDetailsUI(
    tvShowDetails: TVShowDetails,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("TV Show: \n${tvShowDetails}")
    }
}

@Preview
@Composable
fun PreviewTvShows() {
    JetMoviesTheme {
        Surface() {
            TvShowDetailsUI(
                TVShowDetails(
                    id = 1,
                    originalName = "originalName",
                    overview = "overView",
                    vote = 1f,
                    seasons = emptyList()
                )
            )
        }
    }
}
