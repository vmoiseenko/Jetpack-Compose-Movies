package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class RequestTokenRequestBody(
    @SerializedName(value = "request_token")
    val requestToken: String
)
