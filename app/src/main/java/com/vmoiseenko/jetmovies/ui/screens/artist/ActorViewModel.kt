package com.vmoiseenko.jetmovies.ui.screens.artist

import androidx.lifecycle.viewModelScope
import com.vmoiseenko.jetmovies.domain.network.proxy.ApiError
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import com.vmoiseenko.jetmovies.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : BaseViewModel<ActorContract.UiState>() {

    private val viewModelState =
        MutableStateFlow<ActorContract.UiState>(ActorContract.UiState.Loading)

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            ActorContract.UiState.Loading
        )

    fun getPerson(id: Int) {
        viewModelScope.launch {
            val person = moviesRepository.getPerson(id)
            viewModelState.update {
                person.fold(
                    { ActorContract.UiState.Success(it) },
                    { ActorContract.UiState.Error(it as ApiError) }
                )
            }
        }
    }
}