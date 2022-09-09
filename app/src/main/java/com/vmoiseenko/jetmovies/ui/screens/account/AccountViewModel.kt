package com.vmoiseenko.jetmovies.ui.screens.account

import androidx.lifecycle.viewModelScope
import com.vmoiseenko.jetmovies.domain.network.model.Account
import com.vmoiseenko.jetmovies.domain.network.model.CredentialsRequestBody
import com.vmoiseenko.jetmovies.domain.network.model.RequestTokenRequestBody
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import com.vmoiseenko.jetmovies.ui.screens.base.BaseViewModel
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val moviesClient: MoviesClient
) : BaseViewModel<UiState>() {

    private val viewModelState = MutableStateFlow<UiState>(UiState.Initial)

    val uiState: StateFlow<UiState>
        get() = viewModelState

    fun login(username: String, password: String) {
        viewModelState.update {
            UiState.Loading
        }
        viewModelScope.launch {
            try {
                val requestToken = moviesClient.getRequestToken().getOrThrow().requestToken
                val validateToken = moviesClient.validateToken(
                    CredentialsRequestBody(
                        username,
                        password,
                        requestToken
                    )
                ).getOrThrow().requestToken
                val sessionId = moviesClient.createSession(
                    RequestTokenRequestBody(
                        validateToken
                    )
                ).getOrThrow().sessionId
                val accountId = moviesClient.getAccount(sessionId).getOrThrow()
//                val favorites = moviesClient.getFavoriteMovies(accountId, sessionId).getOrThrow()
                viewModelState.update {
                    UiState.SignedIn(accountId)
                }
            } catch (e: Exception) {
                viewModelState.update {
                    UiState.Error(e.toString())
                }
            }
        }
    }
}

sealed class UiState : UIStateBase {
    object Initial : UiState()
    object Loading : UiState()
    data class SignedIn(val account: Account) : UiState()
    data class Error(val message: String) : UiState()
}