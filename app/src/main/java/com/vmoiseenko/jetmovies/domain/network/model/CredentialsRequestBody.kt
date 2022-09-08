package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class CredentialsRequestBody(
    val username: String,
    val password: String,
    @SerializedName(value = "request_token")
    val requestToken: String
)
