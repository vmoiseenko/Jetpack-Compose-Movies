package com.vmoiseenko.jetmovies.ui.screens.details

import androidx.annotation.StringRes
import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.domain.network.proxy.ApiError
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase

interface MovieDetailsContract {

    sealed class UiState: UIStateBase {
        object Loading : UiState()
        data class Success(
            val details: MovieDetails,
            val credits: MovieCredits,
            val isFavorite: Boolean
        ) : UiState()

        data class Error(val apiError: ApiError) : UiState()
    }

    sealed class UiEvent {
        data class ShowSnackbar(@StringRes val messageResId: Int) : UiEvent()
    }
}
