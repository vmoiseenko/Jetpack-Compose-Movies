package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.theme.GrayLight
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun OfflineState(
    buttonAction: Pair<String, () -> Unit>,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Icon(
            imageVector = Icons.Outlined.CloudOff,
            contentDescription = "Offline",
            tint = GrayLight,
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .size(96.dp),
        )
        Text(
            text = stringResource(id = R.string.movies_offline),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                buttonAction.second()
            },
            modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(buttonAction.first)
        }
    }
}


@Preview
@Composable
fun PreviewOfflineState() {
    JetMoviesTheme {
        Surface {
            OfflineState("Retry" to {})
        }
    }
}
