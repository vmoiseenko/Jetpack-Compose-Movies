package com.vmoiseenko.jetmovies.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AlertDialogWrapper(
    title: String,
    message: String,
    positiveButton: Pair<String, () -> Unit>,
    negativeButton: Pair<String, () -> Unit>,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = title)
        },
        text = {
            Text(message)
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        onDismiss()
                        positiveButton.second()
                    }
                ) {
                    Text(positiveButton.first)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        onDismiss()
                        negativeButton.second()
                    }
                ) {
                    Text(negativeButton.first)
                }
            }
        }
    )
}
