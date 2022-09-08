package com.vmoiseenko.jetmovies.domain.network.proxy

import com.vmoiseenko.jetmovies.domain.network.model.*
import retrofit2.http.*

interface MoviesClient {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Result<RequestToken>

    @POST("authentication/token/validate_with_login")
    suspend fun validateToken(@Body sessionRequestBody: CredentialsRequestBody): Result<RequestToken>

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestTokenRequestBody: RequestTokenRequestBody): Result<Session>

    @GET("account")
    suspend fun getAccount(
        @Query("session_id") sessionId: String
    ): Result<Account>

    @GET("account/{accountId}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("accountId") accountId: Int,
        @Query("session_id") sessionId: String
    ): Result<Movies>

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
