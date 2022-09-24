package com.vmoiseenko.jetmovies.ui.screens.tv

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vmoiseenko.jetmovies.domain.network.proxy.ApiError
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import com.vmoiseenko.jetmovies.ui.navigation.TvShowDetails
import com.vmoiseenko.jetmovies.ui.screens.details.MovieDetailsContract
import com.vmoiseenko.jetmovies.ui.screens.tv.TvShowDetailsContract.UiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    moviesRepository: MoviesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val viewModelState = MutableStateFlow<TvShowDetailsContract.UiState>(Loading)

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MovieDetailsContract.UiState.Loading
        )

    init {
        val id = checkNotNull(savedStateHandle.get<Int>(TvShowDetails.tvId))

        viewModelScope.launch {
            val result = moviesRepository.getTvShowDetails(id)
            viewModelState.update {
                result.fold(
                    { TvShowDetailsContract.UiState.Success(it) },
                    { TvShowDetailsContract.UiState.Error(it as ApiError) }
                )
            }
        }
    }
}