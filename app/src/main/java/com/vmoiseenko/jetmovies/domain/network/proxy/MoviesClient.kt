package com.vmoiseenko.jetmovies.domain.network.proxy

import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.domain.network.model.Movies
import com.vmoiseenko.jetmovies.domain.network.model.Person
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesClient {

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int
    ): Result<Movies>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String
    ): Result<Movies>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Result<MovieDetails>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int
    ): Result<MovieCredits>

    @GET("person/{personId}")
    suspend fun getPerson(
        @Path("personId") personId: Int
    ): Result<Person>
}
