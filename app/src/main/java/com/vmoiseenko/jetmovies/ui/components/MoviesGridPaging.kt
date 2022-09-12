package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.ui.theme.GrayLight
import com.vmoiseenko.jetmovies.ui.theme.MovieDetailsTransparentBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.cardShape

@Composable
fun MoviesGridPaging(
    items: LazyPagingItems<MovieItem>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    val state = items.rememberLazyListState()

    LazyVerticalGrid(
        state = state,
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        items(
            items.itemCount
        ) { index ->
            items[index]?.let { movie ->
                MovieCard(
                    movie = movie,
                    onClickListener = { onMovieClick(movie.id) }
                )
            }
        }

        items.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item {
                        Spacer(Modifier)
                    }
                    item {
                        Box(modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier
                                    .align(Alignment.TopCenter)
                                    .padding(top = 32.dp)
                            )
                        }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(172.dp)
                                .placeholder(
                                    visible = true,
                                    color = MovieDetailsTransparentBackgroundColor,
                                    // optional, defaults to RectangleShape
                                    shape = cardShape,
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = GrayLight
                                    )
                                )
                        )
                    }
                }
                loadState.refresh is LoadState.Error -> {
                    val e = items.loadState.refresh as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            modifier = Modifier.fillMaxSize(),
                            onRetryClick = { retry() }
                        )
                    }
                }
                loadState.append is LoadState.Error -> {
                    val e = items.loadState.append as LoadState.Error
                    item {
                        ErrorItem(
                            message = e.error.localizedMessage!!,
                            onRetryClick = { retry() }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyGridState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    // This behavior/issue is resetting the LazyListState scroll position.
    // Below is a workaround. More info: https://issuetracker.google.com/issues/177245496.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> remember(this) { LazyGridState(0, 0) }
        // Return rememberLazyListState (normal case).
        else -> rememberLazyGridState()
    }
}
