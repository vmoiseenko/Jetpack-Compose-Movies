package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.domain.network.model.Cast
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun CastCard(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(72.dp)
    ) {

        CoilImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(72.dp)
                .padding(all = 4.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = Color.White,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.paddingFromBaseline(top = 20.dp, bottom = 8.dp)
        )
    }
}

@Composable
fun CastRow(data: List<Cast>, state: LazyListState, modifier: Modifier = Modifier) {
    LazyRow(
        state = state,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    )
    {
        items(data) { item ->
            CastCard(imageUrl = item.imagePath(), title = item.name)
        }
    }
}


@Preview
@Composable
fun PreviewCastCard() {
    JetMoviesTheme() {
        Surface {
            CastCard("", "Title")
        }
    }
}

@Preview
@Composable
fun PreviewCastCards() {
    JetMoviesTheme() {
        Surface {
            CastRow(
                listOf(
                    Cast(1, "Name Surname", "", "Character"),
                    Cast(1, "Title Surname", "", "Character")
                ),
                rememberLazyListState()
            )
        }
    }
}
