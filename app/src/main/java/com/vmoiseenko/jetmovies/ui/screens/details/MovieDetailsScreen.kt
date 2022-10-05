package com.vmoiseenko.jetmovies.ui.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.domain.network.model.*
import com.vmoiseenko.jetmovies.ui.components.*
import com.vmoiseenko.jetmovies.ui.screens.details.MovieDetailsContract.UiClickEvent.Artist
import com.vmoiseenko.jetmovies.ui.screens.details.MovieDetailsContract.UiClickEvent.Favorite
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MovieDetailsTransparentBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.minutes

@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onCharacterClick: (Int) -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val viewState by viewModel.uiState.collectAsState()
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    var isShowDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    viewModel.getMovies(movieId)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier
    ) {
        when (viewState) {
            is MovieDetailsContract.UiState.Error -> {
//                isShowDialog = true
//                if (isShowDialog) {
//                    AlertDialogWrapper(
//                        title = "Error",
//                        message = (viewState as MovieDetailsContract.UiState.Error).toString(),
//                        positiveButton = "Retry" to {
//                            viewModel.getMovies(movieId)
//                        },
//                        negativeButton = "Close" to { },
//                        onDismiss = { isShowDialog = false }
//                    )
//                }
                OfflineState(
                    buttonAction = "Retry" to {
                        viewModel.getMovies(movieId)
                    }
                )
            }
            MovieDetailsContract.UiState.Loading -> {
                LoadingState(modifier)
            }
            is MovieDetailsContract.UiState.Success -> {
                MovieDetailsSuccess(
                    viewState as MovieDetailsContract.UiState.Success,
                    { uiClickEvent ->
                        when (uiClickEvent) {
                            is Favorite -> {
                                viewModel.favoriteButtonClicked(
                                    uiClickEvent.movieId,
                                    uiClickEvent.isFavorite
                                )
                            }
                            is Artist -> {
                                onCharacterClick(uiClickEvent.artistId)
                            }
                        }
                    },
                    modifier
                )
            }
        }
    }

    LaunchedEffect(context, true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is MovieDetailsContract.UiEvent.ShowSnackbar -> {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.apply {
                            currentSnackbarData?.dismiss()
                            showSnackbar(context.getString(event.messageResId))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetailsSuccess(
    viewState: MovieDetailsContract.UiState.Success,
    onUiClickEvent: (MovieDetailsContract.UiClickEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .verticalScroll(state = scrollState, enabled = true)
            .background(MoviePrimaryBackgroundColor)
            .fillMaxSize()
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                CoilImage(
                    imageUrl = viewState.details.imagePath(),
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

                MovieInfo(
                    title = viewState.details.title,
                    genres = viewState.details.genres.map { it.name },
                    runtime = viewState.details.runtime.minutes.toComponents { hours, minutes, _, _ ->
                        stringResource(R.string.movies_details_runtime, hours, minutes)
                    },
                    rating = viewState.details.vote,
                    isFavorite = viewState.isFavorite,
                    onFavoriteClick = {
                        onUiClickEvent(Favorite(viewState.details.id, it))
                    },
                    Modifier
                        .align(Alignment.BottomCenter)
                        .padding(start = 16.dp, end = 16.dp)
                )
            }

            InfoItem(
                title = stringResource(R.string.movies_details_storyline),
                subtitle = viewState.details.overview,
                modifier = Modifier.padding(top = 0.dp, start = 16.dp, end = 16.dp)
            )

            val notAvailable = stringResource(R.string.movies_details_not_available)
            val director = viewState.credits.crew.mapCrew(DIRECTOR)
                .ifBlank { notAvailable }
            val writer = viewState.credits.crew.mapCrew(WRITER, SCREENPLAY)
                .ifBlank { notAvailable }
            val production = viewState.details.production.joinToString(",\n") { it.name }
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
            CharacterGridRow(
                data = viewState.credits.cast.take(15),
                state = rememberLazyGridState(),
                onCharacterSelected = { onUiClickEvent(Artist(it)) }
            )
        }
    }
}

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

private fun List<Crew>.mapCrew(vararg job: String) =
    this.filter { job.toList().contains(it.job) }.joinToString(separator = ",\n") { it.name }

@Preview
@Composable
fun PreviewMovieDetailsScreen() {
    JetMoviesTheme {
        Surface {
            MovieDetailsSuccess(
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
                        posterPath = "",
                        vote = 6.769f
                    ),
                    credits = MovieCredits(
                        id = 1,
                        cast = listOf(Cast(1, "Name Surname", "", "Character")),
                        crew = listOf(Crew("Name Surname", "Director"))
                    ),
                    true
                ),
                onUiClickEvent = {}
            )
        }
    }
}
