package com.vmoiseenko.jetmovies.domain.network.model

import com.vmoiseenko.jetmovies.BuildConfig

interface Image {

    fun size(): String

    companion object {
        fun Image.imagePath(path: String) = "${BuildConfig.ImagesBaseUrl}${size()}$path"
    }
}

enum class Backdrop(private val size: String) : Image {
    W300("w300"),
    W780("w780"),
    W1280("w1280"),
    ORIGINAL("original");

    override fun size(): String {
        return size
    }
}

enum class Logo(private val size: String) : Image {
    W45("w45"),
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W300("w300"),
    W500("w500"),
    ORIGINAL("original");

    override fun size(): String {
        return size
    }
}

enum class Poster(private val size: String) : Image {
    W92("w92"),
    W154("w154"),
    W185("w185"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORIGINAL("original");

    override fun size(): String {
        return size
    }
}

enum class Profile(private val size: String) : Image {
    W45("w45"),
    W185("w185"),
    H632("h632"),
    ORIGINAL("original");

    override fun size(): String {
        return size
    }
}
