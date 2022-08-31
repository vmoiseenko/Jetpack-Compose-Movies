package com.vmoiseenko.jetmovies.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.domain.network.model.*
import com.vmoiseenko.jetmovies.ui.components.CastRow
import com.vmoiseenko.jetmovies.ui.components.CoilImage
import com.vmoiseenko.jetmovies.ui.components.InfoItem
import com.vmoiseenko.jetmovies.ui.components.MovieInfo
import com.vmoiseenko.jetmovies.ui.theme.MovieDetailsTransparentBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val viewState by viewModel.uiState.collectAsState()

    viewModel.getMovies(movieId)

    MovieDetails(
        viewState = viewState,
        onFavoriteClick = viewModel::favoriteEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalTime::class)
@Composable
private fun MovieDetails(
    viewState: MovieDetailsContract.UiState,
    onFavoriteClick: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    when (viewState) {
        is MovieDetailsContract.UiState.Error -> TODO()
        MovieDetailsContract.UiState.Loading -> {
            Box(modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                )
            }
        }
        is MovieDetailsContract.UiState.Success -> {

            val details = viewState.details

            Box(
                modifier = modifier
                    .verticalScroll(state = scrollState, enabled = true)
                    .background(MoviePrimaryBackgroundColor)
                    .fillMaxSize()
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )
                Column() {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(320.dp)
                    ) {
                        CoilImage(
                            imageUrl = details.imagePath(),
                            modifier = Modifier
                                .fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth()
                                .height(120.dp)
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            MovieDetailsTransparentBackgroundColor,
                                            MoviePrimaryBackgroundColor
                                        )
                                    )
                                )
                        )
                    }

                    InfoItem(
                        title = stringResource(R.string.movies_details_storyline),
                        subtitle = details.overview,
                        modifier = Modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp)
                    )

                    val notAvailable = stringResource(R.string.movies_details_not_available)
                    val director = viewState.credits.crew.mapCrew(DIRECTOR)
                        .ifBlank { notAvailable }
                    val writer = viewState.credits.crew.mapCrew(WRITER, SCREENPLAY)
                        .ifBlank { notAvailable }
                    val production = details.production.joinToString(",\n") { it.name }
                        .ifBlank { notAvailable }

                    InfoRow(
                        data = listOf(
                            stringResource(R.string.movies_details_director) to director,
                            stringResource(R.string.movies_details_screenplay) to writer,
                            stringResource(R.string.movies_details_production) to production
                        ),
                        state = rememberLazyListState()
                    )
                    InfoItem(
                        title = stringResource(id = R.string.movies_details_cast),
                        subtitle = "",
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    CastRow(
                        data = viewState.credits.cast.take(15),
                        state = rememberLazyListState(),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                MovieInfo(
                    title = details.title,
                    genres = details.genres.map { it.name },
                    runtime = details.runtime.minutes.toComponents { hours, minutes, _, _ ->
                        stringResource(R.string.movies_details_runtime, hours, minutes)
                    },
                    rating = details.vote,
                    isFavorite = viewState.isFavorite,
                    onFavoriteClick = {
                        onFavoriteClick(details.id, it)
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(
                                if (it) {
                                    "R.string.movies_details_added_to_fav"
                                } else {
                                    "R.string.movies_details_removed_from_fav"
                                }
                            )
                        }
                    },
                    modifier.padding(start = 16.dp, end = 16.dp, top = 202.dp)
                )

            }
        }
    }

}

private fun List<Crew>.mapCrew(vararg job: String) =
    this.filter { job.toList().contains(it.job) }.joinToString(separator = ",\n") { it.name }

@Composable
fun InfoRow(data: List<Pair<String, String>>, state: LazyListState, modifier: Modifier = Modifier) {
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    )
    {
        items(data) { item ->
            InfoItem(title = item.first, subtitle = item.second)
        }
    }
}

@Preview
@Composable
fun PreviewMovieDetailsScreen() {
    JetMoviesTheme() {
        Surface {
            MovieDetails(
                MovieDetailsContract.UiState.Success(
                    details = MovieDetails(
                        id = 1,
                        title = "Title",
                        overview = "Lorem",
                        genres = listOf(Info(1, "Action")),
                        production = listOf(Info(1, "Columbia Pictures")),
                        runtime = 126,
                        releaseDate = "2022-07-06",
                        backdropPath = "",
                        vote = 6.769f
                    ),
                    credits = MovieCredits(
                        id = 1,
                        cast = listOf(Cast("Name Surname", "")),
                        crew = listOf(Crew("Name Surname", "Director"))
                    ),
                    true
                ),
                onFavoriteClick = { _, _ -> }
            )
        }
    }
}
