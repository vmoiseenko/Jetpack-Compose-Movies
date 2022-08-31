package com.vmoiseenko.jetmovies.domain.repository

import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Named

interface FavoriteRepository {
    fun addToFavorite(id: Int)
    fun removeFromFavorite(id: Int)
    fun isFavorite(id: Int) : Boolean
}

class FavoriteRepositoryImpl @Inject constructor(
    @Named("favoritesPreference") private val sharedPreferences: SharedPreferences
): FavoriteRepository {

    override fun addToFavorite(id: Int) {
        sharedPreferences.edit().putBoolean(id.toString(), true).apply()
    }

    override fun removeFromFavorite(id: Int) {
        sharedPreferences.edit().remove(id.toString()).apply()
    }

    override fun isFavorite(id: Int): Boolean {
        return sharedPreferences.getBoolean(id.toString(), false)
    }
}
