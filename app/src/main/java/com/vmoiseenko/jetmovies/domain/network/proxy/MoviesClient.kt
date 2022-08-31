package com.vmoiseenko.jetmovies.domain.network.proxy

import com.vmoiseenko.jetmovies.domain.network.model.MovieCredits
import com.vmoiseenko.jetmovies.domain.network.model.MovieDetails
import com.vmoiseenko.jetmovies.domain.network.model.Movies
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesClient {

    @GET("discover/movie")
    suspend fun getMovies(): Response<Movies>

    @GET("search/movie")
    suspend fun search(
        @Query("query") query: String
    ): Response<Movies>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int
    ): Response<MovieDetails>

    @GET("movie/{movieId}/credits")
    suspend fun getMovieCredits(
        @Path("movieId") movieId: Int
    ): Response<MovieCredits>
}
