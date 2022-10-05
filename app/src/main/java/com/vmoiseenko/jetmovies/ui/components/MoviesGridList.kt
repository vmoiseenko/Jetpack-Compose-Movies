package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.domain.dto.MovieItem

@Composable
fun MoviesGridList(
    list: List<MovieItem>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(horizontal = 2.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
    ) {
        items(
            items = list,
            key = { it.id }
        ) { movie ->
            MovieCard(
                movie = movie,
                onClickListener = { onMovieClick(movie.id) }
            )
        }
    }
}
