package com.vmoiseenko.jetmovies.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder

@Composable
fun CoilImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
        )

        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.fade(),
                        )
                )
            }
            is AsyncImagePainter.State.Error -> {
                (painter.state as AsyncImagePainter.State.Error).apply {
                    Log.e("COIL", "$imageUrl ${result.request}")
                    Log.e("COIL", result.throwable.stackTraceToString())
                }
                Icon(
                    imageVector = Icons.Default.Error,
                    contentDescription = "",
                    Modifier.align(Alignment.Center)
                )
            }
            else -> {}
        }
        Image(
            painter = painter,
            contentDescription = "image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        )
    }
}
