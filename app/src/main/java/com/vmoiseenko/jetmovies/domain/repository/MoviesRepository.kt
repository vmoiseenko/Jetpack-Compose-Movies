package com.vmoiseenko.jetmovies.domain.repository

import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.domain.network.model.Movies
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import java.io.IOException
import javax.inject.Inject

interface MoviesRepository {
    suspend fun search(query: String): Result<Movies>
    suspend fun getMovies(page: Int): Result<Movies>
    suspend fun getDetails(movieId: Int): Result<MovieDetails>
    suspend fun getCredits(movieId: Int): Result<MovieCredits>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient
) : MoviesRepository {

    override suspend fun getMovies(page: Int): Result<Movies> {
        return try {
            val response = moviesClient.getMovies(page)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable(response.errorBody()?.string()))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun getDetails(movieId: Int): Result<MovieDetails> {
        return Result.success(moviesClient.getMovieDetails(movieId).body()!!)
    }

    override suspend fun search(query: String): Result<Movies> {
        return try {
            val response = moviesClient.search(query)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Throwable(response.errorBody()?.string()))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun getCredits(movieId: Int): Result<MovieCredits> {
        return Result.success(moviesClient.getMovieCredits(movieId).body()!!)
    }
}
