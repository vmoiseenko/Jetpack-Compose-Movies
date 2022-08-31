package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vmoiseenko.jetmovies.ui.theme.MovieDetailsTextColor
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun InfoItem(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MovieDetailsTextColor,
            modifier = Modifier.padding(top = 16.dp, bottom = 6.dp)
        )
        if(subtitle.isNotBlank()){
            Text(
                text = subtitle,
                style = MaterialTheme.typography.body1,
                fontSize = 18.sp,
                color = Color.White,
            )
        }
    }
}

@Preview
@Composable
fun PreviewInfoItem() {
    JetMoviesTheme() {
        Surface {
            InfoItem(title = "title", subtitle = "subtitle")
        }
    }
}
