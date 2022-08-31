package com.vmoiseenko.jetmovies.ui.screens.details

import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase

interface MovieDetailsContract {

    sealed class UiState: UIStateBase {
        object Loading : UiState()
        data class Success(val details: MovieDetails, val credits: MovieCredits, val isFavorite: Boolean) : UiState()
        data class Error(val message: String) : UiState()
    }
}
