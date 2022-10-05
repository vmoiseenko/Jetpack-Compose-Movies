package com.vmoiseenko.jetmovies.domain.repository

import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.domain.dto.mapToItem
import com.vmoiseenko.jetmovies.domain.network.proxy.MoviesClient
import com.vmoiseenko.jetmovies.ui.navigation.Movies.SourceType
import javax.inject.Inject
import javax.inject.Named

interface MoviesProviderRepository {
    suspend fun getMovies(page: Int): Result<Pair<Int, List<MovieItem>>>
}

class MoviesProviderRepositoryBase @Inject constructor(
    @Named("TvShowsProvider") private val tvRepository: MoviesProviderRepository,
    @Named("MoviesProvider") private val moviesRepository: MoviesProviderRepository
) {
    fun get(sourceType: SourceType): MoviesProviderRepository {
        return when (sourceType) {
            SourceType.MOVIE -> moviesRepository
            SourceType.TV_SHOW -> tvRepository
        }
    }
}

class MoviesProviderRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient
) : MoviesProviderRepository {
    override suspend fun getMovies(page: Int): Result<Pair<Int, List<MovieItem>>> {
        return moviesClient.getMovies(page).map { movies ->
            movies.page to movies.results.map {
                it.mapToItem()
            }
        }
    }
}

class TvShowsProviderRepositoryImpl @Inject constructor(
    private val moviesClient: MoviesClient
) : MoviesProviderRepository {
    override suspend fun getMovies(page: Int): Result<Pair<Int, List<MovieItem>>> {
        return moviesClient.getTvShows(page).map { movies ->
            movies.page to movies.results.map {
                it.mapToItem()
            }
        }
    }
}