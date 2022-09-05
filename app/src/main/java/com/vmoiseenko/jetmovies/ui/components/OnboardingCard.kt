package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryLightColor
import com.vmoiseenko.jetmovies.ui.theme.OnboardingColor

@Composable
fun OnboardingCard(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        backgroundColor = OnboardingColor,
        elevation = 4.dp
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.onboarding),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 24.dp, bottom = 16.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.movies_onboarding_title),
                    style = MaterialTheme.typography.h6,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(id = R.string.movies_onboarding_body),
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Button(
                    onClick = onButtonClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MoviePrimaryLightColor,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.movies_onboarding_button),
                        style = MaterialTheme.typography.button
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun PreviewOnboardingCard() {
    JetMoviesTheme() {
        Surface {
            OnboardingCard({})
        }
    }
}
