package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
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
import androidx.compose.ui.text.style.TextOverflow.Companion.Ellipsis
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.domain.network.model.Cast
import com.vmoiseenko.jetmovies.ui.theme.GrayLight
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor

@Composable
fun CharacterCard(
    imageUrl: String,
    title: String,
    character: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .width(172.dp)
            .height(72.dp)
    ) {

        CoilImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .size(72.dp)
                .padding(top = 4.dp, bottom = 4.dp, end = 4.dp)
                .clip(RoundedCornerShape(6.dp))
        )
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Start,
                color = Color.White,
                style = MaterialTheme.typography.caption,
            )
            Text(
                text = character,
                textAlign = TextAlign.Start,
                color = GrayLight,
                maxLines = 2,
                overflow = Ellipsis,
                style = MaterialTheme.typography.overline,
            )
        }
    }
}

@Composable
fun CharacterGridRow(data: List<Cast>, state: LazyGridState, modifier: Modifier = Modifier) {
    LazyHorizontalGrid(
        state = state,
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier.height(152.dp)
    )
    {
        items(data) { item ->
            CharacterCard(
                imageUrl = item.imagePath(),
                title = item.name,
                character = item.character
            )
        }
    }
}


@Preview
@Composable
fun PreviewCharacterCard() {
    JetMoviesTheme() {
        Surface() {
            CharacterCard(
                "", "Tom Cruise", "Capt. Pete 'Maverick' Mitchell",
                Modifier.background(MoviePrimaryBackgroundColor)
            )
        }
    }
}

@Preview
@Composable
fun PreviewCharacterCards() {
    JetMoviesTheme() {
        Surface() {
            CharacterGridRow(
                listOf(
                    Cast("Name Surname", "Tom Cruise", "Capt. Pete 'Maverick' Mitchell"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                    Cast("Title Surname", "Jennifer Connelly", "Penny Benjamin"),
                ),
                rememberLazyGridState(),
                Modifier.background(MoviePrimaryBackgroundColor)
            )
        }
    }
}
