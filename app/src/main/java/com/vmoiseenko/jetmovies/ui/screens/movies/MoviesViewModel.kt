package com.vmoiseenko.jetmovies.ui.screens.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vmoiseenko.jetmovies.domain.network.model.Movie
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val viewModelState =
        MutableStateFlow<MoviesContract.UiState>(MoviesContract.UiState.Loading)

    private var job: Job? = null

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MoviesContract.UiState.Loading
        )

    init {
        getMovies()
    }

    private fun getMovies() {
        viewModelScope.launch {
            val result = repository.getMovies()
            viewModelState.update {
                result.fold(
                    { MoviesContract.UiState.Movies(it) },
                    { MoviesContract.UiState.Error(it.toString()) }
                )
            }
        }
    }

    fun search(query: String) {
        job?.cancel() // cancel prior job on a new change in text
        job = viewModelScope.launch {
            viewModelState.update {
                MoviesContract.UiState.Loading
            }
            delay(500)
            println("Do API call with $query")

            val result = if (query.isNotBlank()) {
                repository.search(query)
            } else {
                repository.getMovies()
            }
            viewModelState.update {
                result.fold(
                    { MoviesContract.UiState.Movies(it) },
                    { MoviesContract.UiState.Error(it.toString()) }
                )
            }
        }
    }

    fun retry() {
        getMovies()
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

