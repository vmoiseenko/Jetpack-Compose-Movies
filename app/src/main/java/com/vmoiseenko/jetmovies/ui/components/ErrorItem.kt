package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.vmoiseenko.jetmovies.ui.theme.Green
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun ErrorItem(
    message: String,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        Text(text = message, color = Color.White)
        Button(
            colors = ButtonDefaults.buttonColors(backgroundColor = Green),
            onClick = { onRetryClick() },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview
@Composable
fun PreviewErrorItem() {
    JetMoviesTheme() {
        Scaffold() {
            ErrorItem(message = "Error message!", onRetryClick = { })
        }
    }
}
