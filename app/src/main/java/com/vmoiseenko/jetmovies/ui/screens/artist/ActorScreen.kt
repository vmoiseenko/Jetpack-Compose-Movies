package com.vmoiseenko.jetmovies.ui.screens.artist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.domain.network.model.Person
import com.vmoiseenko.jetmovies.ui.components.AlertDialogWrapper
import com.vmoiseenko.jetmovies.ui.components.CoilImage
import com.vmoiseenko.jetmovies.ui.components.InfoItem
import com.vmoiseenko.jetmovies.ui.screens.artist.ActorContract.UiState.Loading
import com.vmoiseenko.jetmovies.ui.screens.artist.ActorContract.UiState.Success
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme
import com.vmoiseenko.jetmovies.ui.theme.MoviePrimaryBackgroundColor
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Composable
fun ActorScreen(
    actorId: Int,
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
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .verticalScroll(state = scrollState, enabled = true)
            .fillMaxSize()
            .background(MoviePrimaryBackgroundColor)

    ) {
        Spacer(modifier = Modifier.size(48.dp))
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
            title = "Born",
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
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp)
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
                )
            )
        }
    }
}
