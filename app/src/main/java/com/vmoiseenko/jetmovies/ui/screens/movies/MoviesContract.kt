package com.vmoiseenko.jetmovies.ui.screens.movies

import com.vmoiseenko.jetmovies.domain.network.model.Movie
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase

interface MoviesContract {

    sealed class UiState : UIStateBase {
        object Paging : UiState()
        object Loading : UiState()
        data class Movies(val movies: List<Movie>) : UiState()
        data class Error(val message: String) : UiState()
    }
}
