package com.vmoiseenko.jetmovies.ui.screens.movies

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vmoiseenko.jetmovies.domain.network.model.Movie
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val moviesRepository: MoviesRepository
) : PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val nextPage = params.key ?: 1
        val result = moviesRepository.getMovies(page = nextPage)
        Log.d("Test", result.toString())
        return result.fold(
            {
                LoadResult.Page(
                    data = it.results,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (it.results.isEmpty()) null else it.page + 1
                )
            },
            {
                LoadResult.Error(it)
            }
        )
    }
}
