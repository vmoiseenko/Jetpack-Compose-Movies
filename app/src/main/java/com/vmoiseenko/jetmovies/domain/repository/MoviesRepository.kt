package com.vmoiseenko.jetmovies.domain.repository

import com.vmoiseenko.jetmovies.domain.network.model.Movie
import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import java.io.IOException
import javax.inject.Inject

interface MoviesRepository {
    suspend fun search(query: String): Result<List<Movie>>
    suspend fun getMovies(): Result<List<Movie>>
    suspend fun getDetails(movieId: Int): Result<MovieDetails>
    suspend fun getCredits(movieId: Int): Result<MovieCredits>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient
) : MoviesRepository {

    override suspend fun getMovies(): Result<List<Movie>> {
        return try {
            val response = moviesClient.getMovies()
            if(response.isSuccessful) {
                Result.success(response.body()!!.results)
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

    override suspend fun search(query: String): Result<List<Movie>> {
        return Result.success(moviesClient.search(query).body()!!.results)
    }

    override suspend fun getCredits(movieId: Int): Result<MovieCredits> {
        return Result.success(moviesClient.getMovieCredits(movieId).body()!!)
    }
}
