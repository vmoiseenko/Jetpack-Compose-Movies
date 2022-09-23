package com.vmoiseenko.jetmovies.ui.screens.movies

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vmoiseenko.jetmovies.domain.dto.MovieItem
import com.vmoiseenko.jetmovies.domain.dto.mapToItem
import com.vmoiseenko.jetmovies.domain.repository.MoviesProviderRepositoryBase
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import com.vmoiseenko.jetmovies.ui.navigation.Movies
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    savedStateHandle: SavedStateHandle,
    moviesProviderRepositoryBase: MoviesProviderRepositoryBase
) : ViewModel() {

    private val moviesSource: MoviesPagingSource

    init {
        val argument = checkNotNull(savedStateHandle[Movies.sourceType])

        moviesSource = MoviesPagingSource(
            moviesProviderRepositoryBase.get(
                Movies.SourceType.values().first { it.type == argument }
            )
        )
    }

    private val viewModelState =
        MutableStateFlow<MoviesContract.UiState>(MoviesContract.UiState.Paging)

    val pagingMoviesFlow: Flow<PagingData<MovieItem>> = Pager(
        PagingConfig(pageSize = 1)
    ) {
        moviesSource
    }.flow
        .cachedIn(viewModelScope)


    private var job: Job? = null
    private var page: Int = 1
    private var searchQuery: String = ""

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MoviesContract.UiState.Paging
        )

    private fun getMovies() {
        viewModelScope.launch {
            val result = repository.getMovies(page)
            viewModelState.update {
                result.fold(
                    { MoviesContract.UiState.Movies(it.second) },
                    { MoviesContract.UiState.Error(it.toString()) }
                )
            }
        }
    }

    fun search(query: String) {
        searchQuery = query
        job?.cancel() // cancel prior job on a new change in text
        job = viewModelScope.launch {
            viewModelState.update {
                MoviesContract.UiState.Loading
            }
            delay(500)

            if (query.isNotBlank()) {
                println("Do API search call with $query")

                val result = repository.search(query)
                viewModelState.update {
                    result.fold(
                        { MoviesContract.UiState.Movies(it.results.map { it.mapToItem() }) },
                        { MoviesContract.UiState.Error(it.toString()) }
                    )
                }
            } else {
                viewModelState.update {
                    MoviesContract.UiState.Paging
                }
            }
        }
    }

    fun retry() {
        search(searchQuery)
    }

//    private val retryTrigger = RetryTrigger()

//    val itemsFlow = flow<MoviesUiState> {
//        val movies = repository.getMovies()
//        movies.fold(
//            {
//                emit(MoviesUiState.Movies(it.map { movie ->
//                    MovieItem(movie.id, movie.title, movie.overview, movie.imagePath())
//                }))
//            },
//            {
//                emit(MoviesUiState.Error(it.toString()))
//            }
//        )
//    }

//    val uiState2 = retryableFlow(retryTrigger) {
//        flow<MoviesUiState> {
//            val movies = repository.getMovies()
//            movies.fold(
//                {
//                    emit(MoviesUiState.Movies(it.mapIndexed { index, movie ->
//                        MovieItem(index, movie.title, movie.overview, movie.imagePath())
//                    }))
//                },
//                {
//                    emit(MoviesUiState.Error(it.toString()))
//                }
//            )
//        }
//    }
}

