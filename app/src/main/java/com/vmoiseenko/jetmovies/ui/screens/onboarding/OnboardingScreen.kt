package com.vmoiseenko.jetmovies.ui.screens.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.ui.components.OnboardingCard
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MovieSecondaryLightColor

@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(MovieSecondaryLightColor)
            .padding(16.dp)
    ) {
        OnboardingCard(
            onButtonClick = onContinueClicked
        )
    }
}

@Preview
@Composable
fun PreviewOnboarding() {
    JetMoviesTheme {
        OnboardingScreen({})
    }
}
