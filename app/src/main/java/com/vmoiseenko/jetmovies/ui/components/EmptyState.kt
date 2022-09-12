package com.vmoiseenko.jetmovies.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun EmptyState(
    @StringRes title: Int,
    @DrawableRes image: Int,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 24.dp)
        )
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewEmptyState() {
    JetMoviesTheme {
        Surface {
            EmptyState(
                R.string.favorite_empty,
                R.drawable.onboarding
            )
        }
    }
}