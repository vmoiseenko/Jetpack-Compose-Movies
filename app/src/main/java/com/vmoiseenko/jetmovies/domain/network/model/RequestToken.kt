package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class RequestToken(
    val success: Boolean,
    @SerializedName(value = "expires_at")
    val expiresAt: String?,
    @SerializedName(value = "request_token")
    val requestToken: String
)