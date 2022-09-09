package com.vmoiseenko.jetmovies.ui.screens.account

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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

    if (viewState !is UiState.SignedIn) {
        SignInScreen(
            onLogin = viewModel::login,
            modifier = modifier,
            viewState = viewState
        )
    } else {
        UserScreen(
            userName = (viewState as UiState.SignedIn).account.name,
            onSignOut = viewModel::logout
        )
    }
}

@Composable
fun SignInScreen(
    onLogin: (String, String) -> Unit,
    viewState: UiState = UiState.Initial,
    modifier: Modifier = Modifier,
) {
    var username by remember { mutableStateOf(TextFieldValue()) }
    var password by remember { mutableStateOf(TextFieldValue()) }

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
        TextFieldWrapper(
            title = R.string.account_username,
            leadingIcon = Icons.Filled.VerifiedUser,
            value = username,
            onValueChange = { username = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextFieldWrapper(
            title = R.string.account_password,
            leadingIcon = Icons.Filled.Lock,
            value = password,
            onValueChange = { password = it },
            isPassword = true
        )
        Box(modifier = Modifier.padding(16.dp)) {
            Button(
                onClick = { onLogin(username.text, password.text) },
                enabled = username.text.isNotBlank() && password.text.isNotBlank(),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                when (viewState) {
                    is UiState.Loading -> {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier
                                .size(36.dp)
                                .align(CenterVertically)
                        )
                    }
                    else -> {
                        Text(text = stringResource(id = R.string.account_sign_in))
                    }
                }
            }
        }

        if (viewState is UiState.Error) {
            Text(
                text = "Login failed\n${viewState.message}"
            )
        }
    }
}

@Composable
fun UserScreen(
    userName: String,
    onSignOut: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.account_welcome, userName),
            style = MaterialTheme.typography.h4,

            )
        Spacer(modifier = Modifier.size(16.dp))
        Button(
            onClick = { onSignOut() },
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(text = stringResource(id = R.string.account_sign_out))
        }
    }
}

@Composable
fun TextFieldWrapper(
    @StringRes title: Int,
    leadingIcon: ImageVector,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    isPassword: Boolean = false,
    modifier: Modifier = Modifier
) {

    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }

    TextField(
        label = {
            Text(text = stringResource(id = title))
        },
        value = value,
        leadingIcon = {
            Icon(imageVector = leadingIcon, null)
        },
        trailingIcon = {
            if (isPassword) {
                val image = when {
                    isPasswordVisible -> Icons.Filled.Visibility
                    else -> Icons.Filled.VisibilityOff
                }
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(imageVector = image, null)
                }
            }
        },
        visualTransformation = when {
            isPassword && isPasswordVisible -> VisualTransformation.None
            isPassword -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        keyboardOptions = when {
            isPassword -> {
                KeyboardOptions(
                    keyboardType = KeyboardType.Password
                )
            }
            else -> {
                KeyboardOptions.Default
            }
        },
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(24.dp),
        colors = TextFieldDefaults.textFieldColors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
    )
}

@Preview
@Composable
fun PreviewSignInScreen() {
    JetMoviesTheme {
        Surface {
            SignInScreen({ _: String, _: String -> })
        }
    }
}

@Preview
@Composable
fun PreviewUserScreen() {
    JetMoviesTheme {
        Surface {
            UserScreen("Android", {})
        }
    }
}
