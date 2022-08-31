package com.vmoiseenko.jetmovies.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.ui.theme.Green
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(text = "Welcome to Compose!!")
        Button(
            modifier = Modifier
                .padding(vertical = 24.dp)
            ,
            colors = ButtonDefaults.buttonColors(backgroundColor = Green),
            onClick = onContinueClicked
        ) {
            Text(color = Color.White, text = "Continue")
        }
    }
}

@Preview
@Composable
fun PreviewOnboarding() {
    JetMoviesTheme {
        OnboardingScreen {}
    }
}
