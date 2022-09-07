package com.vmoiseenko.jetmovies.domain.network.model

import com.google.gson.annotations.SerializedName
import com.vmoiseenko.jetmovies.domain.network.model.Image.Companion.imagePath

data class Person(
    val id: Int,
    val name: String,
    val biography: String,
    val birthday: String,
    @SerializedName(value = "place_of_birth")
    val placeOfBirth: String,
    val popularity: Float,
    @SerializedName(value = "profile_path")
    val profileImage: String
) {
    fun imagePath(): String = Profile.H632.imagePath(profileImage)
}