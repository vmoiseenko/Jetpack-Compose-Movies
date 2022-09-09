package com.vmoiseenko.jetmovies.ui.screens.account

import androidx.lifecycle.viewModelScope
import com.vmoiseenko.jetmovies.domain.network.model.Account
import com.vmoiseenko.jetmovies.domain.repository.UserRepository
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
    private val userRepository: UserRepository
) : BaseViewModel<UiState>() {

    private val viewModelState = MutableStateFlow<UiState>(UiState.Initial)

    val uiState: StateFlow<UiState>
        get() = viewModelState

    init {
        viewModelScope.launch {
            userRepository.getSession()?.let { (account, _) ->
                viewModelState.update {
                    UiState.SignedIn(account)
                }
            }
        }
    }

    fun login(username: String, password: String) {
        viewModelState.update {
            UiState.Loading
        }
        viewModelScope.launch {
            try {
                val account = userRepository.signIn(username, password)
                viewModelState.update {
                    UiState.SignedIn(account)
                }
            } catch (e: Exception) {
                viewModelState.update {
                    UiState.Error(e.toString())
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            viewModelState.update {
                UiState.Initial
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