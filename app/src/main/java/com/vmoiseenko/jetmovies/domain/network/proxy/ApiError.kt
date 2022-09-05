package com.vmoiseenko.jetmovies.domain.network.proxy

data class ApiError(
    val isNoInternet: Boolean = false,
    val httpCode: Int = -1,
    val throwable: Throwable
) : Exception(throwable)

