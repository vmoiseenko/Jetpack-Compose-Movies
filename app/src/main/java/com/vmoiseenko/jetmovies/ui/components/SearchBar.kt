package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.theme.*

@Composable
fun SearchBar(
    onValueChange: (String) -> Unit = {}
) {

    var value by remember { mutableStateOf<String>("") }

    TextField(
        value = value,
        onValueChange = {
            value = it
            onValueChange(value)
        },
        trailingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = Color.White)
        },
        placeholder = {
            Text(text = stringResource(id = R.string.placeholder_search), color = Color.White)
        },
        colors = TextFieldDefaults.textFieldColors(textColor = Color.White),
        modifier = Modifier
            .background(color = MoviePrimaryLightColor)
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Preview
@Composable
fun PreviewSearchBar() {
    JetMoviesTheme() {
        Surface() {
            SearchBar()
        }
    }
}
