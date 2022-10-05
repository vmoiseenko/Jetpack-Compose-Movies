package com.vmoiseenko.jetmovies.domain.repository

import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.domain.dto.mapToItem
import com.vmoiseenko.jetmovies.domain.network.model.*
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import javax.inject.Inject

interface MoviesRepository {
    suspend fun search(query: String): Result<Movies>
    suspend fun getMovies(page: Int): Result<Pair<Int, List<MovieItem>>>
    suspend fun getMovieDetails(movieId: Int): Result<MovieDetails>
    suspend fun getTvShowDetails(tvId: Int): Result<TVShowDetails>
    suspend fun getCredits(movieId: Int): Result<MovieCredits>
    suspend fun getPerson(id: Int): Result<Person>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient
) : MoviesRepository {

    override suspend fun getMovies(page: Int): Result<Pair<Int, List<MovieItem>>> {
        return moviesClient.getMovies(page).map { movies ->
            movies.page to movies.results.map {
                it.mapToItem()
            }
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Result<MovieDetails> {
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

    override suspend fun getTvShowDetails(tvId: Int): Result<TVShowDetails> {
        return moviesClient.getTvShowDetails(tvId)
    }
}
