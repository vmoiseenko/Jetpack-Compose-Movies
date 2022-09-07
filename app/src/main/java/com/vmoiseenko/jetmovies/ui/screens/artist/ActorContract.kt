package com.vmoiseenko.jetmovies.ui.screens.artist

import com.vmoiseenko.jetmovies.domain.network.model.Person
import com.vmoiseenko.jetmovies.domain.network.proxy.ApiError
import com.vmoiseenko.jetmovies.ui.screens.base.UIStateBase

interface ActorContract {

    sealed class UiState : UIStateBase {
        object Loading : ActorContract.UiState()
        data class Success(val person: Person) : UiState()
        data class Error(val apiError: ApiError) : UiState()
    }
}