package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName

data class Session(
    val success: Boolean,
    @SerializedName(value = "session_id")
    val sessionId: String
)
