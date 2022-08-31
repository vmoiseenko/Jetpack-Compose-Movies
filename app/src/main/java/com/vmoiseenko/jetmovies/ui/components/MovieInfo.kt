package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vmoiseenko.jetmovies.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MovieInfo(
    title: String,
    genres: List<String>,
    runtime: String,
    rating: Float,
    isFavorite: Boolean,
    onFavoriteClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    var favoriteState by remember { mutableStateOf(isFavorite) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(MovieTransparent, RoundedCornerShape(32.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = CenterVertically
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.h6,
                    color = Color.White,
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        favoriteState = !favoriteState
                        onFavoriteClick(favoriteState)
                    }) {
                    Icon(
                        imageVector = if (favoriteState) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        tint = Color.White,
                        contentDescription = "Favorite"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(
                mainAxisSpacing = 8.dp,
                crossAxisSpacing = 8.dp
            ) {
                genres.forEach {
                    MovieChip(text = it, color = MovieBlue)
                }
                MovieChip(text = runtime, color = MovieFuxia)
                MovieChip(text = "%.1f ★".format(rating), color = MovieYellow)
            }
        }
    }
}

@Composable
fun MovieChip(text: String, color: Color, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        fontSize = 15.sp,
        modifier = modifier
            .background(color, RoundedCornerShape(16.dp))
            .padding(start = 12.dp, end = 12.dp, top = 4.dp, bottom = 4.dp),
    )
}

@Preview
@Composable
fun PreviewMovieInfo() {
    JetMoviesTheme() {
        Surface(color = Color.Black) {
            MovieInfo("Venom 2", listOf("Action", "Drama"), "2.30 Hour", 8.8f, false, {})
        }
    }
}
