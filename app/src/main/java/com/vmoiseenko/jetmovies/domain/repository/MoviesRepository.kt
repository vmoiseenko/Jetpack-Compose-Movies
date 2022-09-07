package com.vmoiseenko.jetmovies.domain.repository

import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.domain.network.model.Movies
import com.vmoiseenko.jetmovies.domain.network.model.Person
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import javax.inject.Inject

interface MoviesRepository {
    suspend fun search(query: String): Result<Movies>
    suspend fun getMovies(page: Int): Result<Movies>
    suspend fun getDetails(movieId: Int): Result<MovieDetails>
    suspend fun getCredits(movieId: Int): Result<MovieCredits>
    suspend fun getPerson(id: Int): Result<Person>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient
) : MoviesRepository {

    override suspend fun getMovies(page: Int): Result<Movies> {
        return moviesClient.getMovies(page)

//        return try {
//            val response = moviesClient.getMovies(page)
//            if (response.isSuccessful) {
//                Result.success(response.body()!!)
//            } else {
//                Result.failure(Throwable(response.errorBody()?.string()))
//            }
//        } catch (e: IOException) {
//            Result.failure(e)
//        }
    }

    override suspend fun getDetails(movieId: Int): Result<MovieDetails> {
        return moviesClient.getMovieDetails(movieId)
    }

    override suspend fun search(query: String): Result<Movies> {
        return moviesClient.search(query)
    }

    override suspend fun getCredits(movieId: Int): Result<MovieCredits> {
        return moviesClient.getMovieCredits(movieId)
    }

    override suspend fun getPerson(id: Int): Result<Person> {
        return moviesClient.getPerson(id)
    }
}
