package com.vmoiseenko.jetmovies.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.ui.theme.Green
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun FavoritesScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        var counter by remember {
            mutableStateOf(0)
        }

        Text(
            text =
            if (counter == 0) {
                "Favorites screen is under development."
            } else {
                "Clicked $counter times"
            }
        )
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Green),
            onClick = { counter++ }
        ) {
            Text(color = Color.White, text = "Ok")
        }
    }
}

@Preview
@Composable
fun PreviewFavorites() {
    JetMoviesTheme {
        Surface() {
            FavoritesScreen()
        }
    }
}
