package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.vmoiseenko.jetmovies.ui.theme.BottomBackgroundColor
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun MyBottomNavigation() {
    BottomNavigation(
        backgroundColor = BottomBackgroundColor
    ) {
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.Spa, contentDescription = null)
            },
            label = {
                Text(text = "Home")
            },
            selected = true,
            onClick = { /*TODO*/ }
        )
        BottomNavigationItem(
            icon = {
                Icon(imageVector = Icons.Default.AccountCircle, contentDescription = null)
            },
            label = {
                Text(text = "Profile")
            },
            selected = true,
            onClick = { /*TODO*/ }
        )
    }
}

@Preview
@Composable
fun PreviewMyBottomNavigation() {
    JetMoviesTheme() {
        Surface() {
            MyBottomNavigation()
        }
    }
}
