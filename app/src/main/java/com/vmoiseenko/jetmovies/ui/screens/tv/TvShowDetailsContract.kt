package com.vmoiseenko.jetmovies.ui.screens.tv

import com.vmoiseenko.jetmovies.domain.network.model.TVShowDetails
import com.vmoiseenko.jetmovies.domain.network.proxy.ApiError
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase

class TvShowDetailsContract {

    sealed class UiState() : UIStateBase {
        object Loading : UiState()
        data class Success(
            val details: TVShowDetails,
        ) : UiState()

        data class Error(val apiError: ApiError) : UiState()
    }
}