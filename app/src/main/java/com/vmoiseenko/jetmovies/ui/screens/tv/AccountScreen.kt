package com.vmoiseenko.jetmovies.ui.screens.tv

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun AccountScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text("Account")
    }
}

@Preview
@Composable
fun PreviewAccountScreen() {
    JetMoviesTheme {
        Surface() {
            AccountScreen()
        }
    }
}
