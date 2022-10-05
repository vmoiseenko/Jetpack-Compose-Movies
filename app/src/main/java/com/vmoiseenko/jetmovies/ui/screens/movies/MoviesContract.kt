package com.vmoiseenko.jetmovies.ui.screens.movies

import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase

interface MoviesContract {

    sealed class UiState : UIStateBase {
        object Paging : UiState()
        object Loading : UiState()
        data class Movies(val movies: List<MovieItem>) : UiState()
        data class Error(val message: String) : UiState()
    }
}
