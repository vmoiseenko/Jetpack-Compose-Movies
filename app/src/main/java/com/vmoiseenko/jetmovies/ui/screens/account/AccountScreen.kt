package com.vmoiseenko.jetmovies.ui.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

    val viewState by viewModel.uiState.collectAsState()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            label = { Text(text = "Username") },
            value = username,
            onValueChange = { username = it }
        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            label = { Text(text = "Password") },
            value = password,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { password = it }
        )
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = { viewModel.login(username.text, password.text) },
                enabled = username.text.isNotBlank() && password.text.isNotBlank(),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (viewState is UiState.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colors.onSurface,
                        modifier = Modifier
                            .align(CenterVertically)
                    )
                } else {
                    Text(text = "Sign In")
                }
            }
        }

        when (viewState) {
            is UiState.Error -> {
                Text(
                    text = "Login failed\n${(viewState as UiState.Error).message}"
                )
            }
            is UiState.Movies -> {
                Text(
                    text = "Login successful, there are yours favorite movies\n${(viewState as UiState.Movies).movies}"
                )
            }
            else -> {}
        }

    }
}

@Preview
@Composable
fun PreviewAccountScreen() {
    JetMoviesTheme {
        Surface {
            AccountScreen()
        }
    }
}
