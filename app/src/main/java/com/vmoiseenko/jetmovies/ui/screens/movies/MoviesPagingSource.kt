package com.vmoiseenko.jetmovies.ui.screens.movies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.domain.repository.MoviesProviderRepository
import javax.inject.Inject
import javax.inject.Named

class MoviesPagingSource @Inject constructor(
    @Named("TvShowsProvider") private val moviesRepository: MoviesProviderRepository
) : PagingSource<Int, MovieItem>() {
    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieItem> {
        val nextPage = params.key ?: 1
        return moviesRepository.getMovies(page = nextPage).fold(
            { (page, data) ->
                LoadResult.Page(
                    data = data,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (data.isEmpty()) null else page + 1
                )
            },
            {
                LoadResult.Error(it)
            }
        )
    }
}
