package com.vmoiseenko.jetmovies.ui.screens.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.domain.network.model.Person
import com.vmoiseenko.jetmovies.ui.components.AlertDialogWrapper
import com.vmoiseenko.jetmovies.ui.components.CoilImage
import com.vmoiseenko.jetmovies.ui.components.InfoItem
import com.vmoiseenko.jetmovies.ui.screens.artist.ActorContract.UiState.Loading
import com.vmoiseenko.jetmovies.ui.screens.artist.ActorContract.UiState.Success
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MovieDetailsTextColor
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.MovieTransparent
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ActorScreen(
    actorId: Int,
    onBackPress: () -> Unit,
    viewModel: ActorViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val viewState by viewModel.uiState.collectAsState()
    var isShowDialog by remember { mutableStateOf(true) }

    viewModel.getPerson(actorId)

    when (viewState) {
        Loading -> {}
        is Success -> Actor(
            person = (viewState as Success).person,
            onBackPress = onBackPress,
            modifier = modifier
        )
        is ActorContract.UiState.Error -> {
            if (isShowDialog) {
                AlertDialogWrapper(
                    title = "Error",
                    message = (viewState as ActorContract.UiState.Error).toString(),
                    positiveButton = "Ok" to { },
                    negativeButton = "Close" to { },
                    onDismiss = { isShowDialog = false }
                )
            }
        }
    }
}

@Composable
fun Actor(
    person: Person,
    onBackPress: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var isShowMore by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState, enabled = true)
            .fillMaxSize()
            .background(MoviePrimaryBackgroundColor)

    ) {
        IconButton(
            onClick = { onBackPress() },
            modifier = Modifier
                .padding(16.dp)
                .size(36.dp)
                .background(
                    color = MovieTransparent,
                    shape = CircleShape
                )
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.White
            )
        }

        CoilImage(
            imageUrl = person.imagePath(),
            modifier = Modifier
                .size(172.dp)
                .align(CenterHorizontally)
                .clip(CircleShape)
        )
        Text(
            text = person.name,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 16.dp)
        )
        InfoItem(
            title = stringResource(id = R.string.actor_born),
            subtitle = "${person.placeOfBirth}\n${
                LocalDate
                    .parse(person.birthday)
                    .format(
                        DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.US)
                    )
            }",
            modifier = Modifier.padding(start = 16.dp)
        )
        Text(
            text = person.biography,
            style = MaterialTheme.typography.body1,
            color = Color.White,
            maxLines = if (isShowMore) 5 else Int.MAX_VALUE,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
        )

        Text(
            text = stringResource(
                if (isShowMore) R.string.actor_show_more
                else R.string.actor_show_less
            ),
            style = MaterialTheme.typography.button,
            textAlign = TextAlign.Center,
            color = MovieDetailsTextColor,
            modifier = Modifier
                .heightIn(24.dp)
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { isShowMore = !isShowMore }
        )
    }
}

@Preview
@Composable
fun PreviewActorScreen() {
    JetMoviesTheme() {
        Surface {
            Actor(
                Person(
                    id = 1,
                    name = "Brad Pitt",
                    biography = "William Bradley \"Brad\" Pitt (born December 18, 1963) is an American actor an",
                    birthday = "1963-12-18",
                    placeOfBirth = "Shawnee, Oklahoma, USA",
                    popularity = 86.36f,
                    profileImage = ""
                ),
                {}
            )
        }
    }
}
