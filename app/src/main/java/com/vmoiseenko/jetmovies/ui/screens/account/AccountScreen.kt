package com.vmoiseenko.jetmovies.ui.screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vmoiseenko.jetmovies.R
import com.vmoiseenko.jetmovies.ui.theme.JetMoviesTheme

@Composable
fun AccountScreen(
    viewModel: AccountViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val viewState by viewModel.uiState.collectAsState()

    AccountScreenView(
        onLogin = viewModel::login,
        modifier = modifier,
        viewState = viewState
    )
}

@Composable
fun AccountScreenView(
    onLogin: (String, String) -> Unit,
    viewState: UiState = UiState.Initial,
    modifier: Modifier = Modifier,
) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.account_login_now),
            style = MaterialTheme.typography.h4,
        )
        Text(
            text = stringResource(id = R.string.account_description),
            style = MaterialTheme.typography.caption,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        TextField(
            label = { Text(text = stringResource(id = R.string.account_username)) },
            value = username,
            onValueChange = { username = it },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            label = { Text(text = stringResource(id = R.string.account_password)) },
            value = password,
            visualTransformation = if (passwordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            onValueChange = { password = it },
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility
                else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, null)
                }
            },
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .fillMaxWidth()
        )
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = { onLogin(username.text, password.text) },
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
                    Text(text = stringResource(id = R.string.account_sign_in))
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
            AccountScreenView({ _: String, _: String -> })
        }
    }
}
