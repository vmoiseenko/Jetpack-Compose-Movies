package com.vmoiseenko.jetmovies.ui.screens.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.ui.components.CoilImage
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor

@Composable
fun ArtistScreen(
    artistId: Int,
    modifier: Modifier = Modifier
) {

    val scrollState = rememberScrollState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .verticalScroll(state = scrollState, enabled = true)
            .fillMaxSize()
            .background(MoviePrimaryBackgroundColor)

    ) {
        Spacer(modifier = Modifier.size(96.dp))
        CoilImage(
            imageUrl = "",
            modifier = Modifier
                .size(164.dp)
                .clip(CircleShape)
        )
        Text(
            text = "Cristopher Nolan $artistId",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewArtistScreen() {
    JetMoviesTheme() {
        Surface {
            ArtistScreen(0)
        }
    }
}
