package com.vmoiseenko.jetmovies.ui.screens.details

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.vmoiseenko.jetmovies.domain.repository.FavoriteRepository
import com.vmoiseenko.jetmovies.domain.repository.MoviesRepository
import com.vmoiseenko.jetmovies.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val favoriteRepository: FavoriteRepository,
) : BaseViewModel<MovieDetailsContract.UiState>() {

    private val viewModelState =
        MutableStateFlow<MovieDetailsContract.UiState>(MovieDetailsContract.UiState.Loading)

    private var job: Job? = null

    // UI state exposed to the UI
    val uiState = viewModelState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            MovieDetailsContract.UiState.Loading
        )


    fun getMovies(id: Int) {
        viewModelScope.launch {
            val detailsFlow = flowOf(moviesRepository.getDetails(id))
            val creditsFlow = flowOf(moviesRepository.getCredits(id))
            val isFavorite = favoriteRepository.isFavorite(id)

            detailsFlow.zip(creditsFlow) { details, credits ->
                viewModelState.update {
                    try {
                        MovieDetailsContract.UiState.Success(details.getOrThrow(), credits.getOrThrow(), isFavorite)
                    } catch (e: Exception) {
                        MovieDetailsContract.UiState.Error(e.toString())
                    }
                }
            }.collect()
        }
    }

    fun favoriteEvent(id: Int, isAdd: Boolean) {
        if(isAdd) {
            Log.d("DEBUG", "Add to FAV clicked $id")
            favoriteRepository.addToFavorite(id)
        } else {
            Log.d("DEBUG", "Remove from FAV $id")
            favoriteRepository.removeFromFavorite(id)
        }
    }

//    fun retry() {
//        getMovies()
//    }

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


//    val tasks: List<WellnessTask>
//        get() = _tasks

//    fun changeTaskChecked(item: WellnessTask, checked: Boolean): Unit? {
//        return tasks.find { it.id == item.id }?.let { task ->
//            task.checked = checked
//        }
//    }
//
//    fun remove(item: WellnessTask) {
//        _tasks.remove(item)
//    }

//    private val _tasks = repository.getTasks().toMutableStateList()
//
//    val tasks: List<WellnessTask>
//        get() = _tasks
//
//    fun changeTaskChecked(item: WellnessTask, checked: Boolean): Unit? {
//        return tasks.find { it.id == item.id }?.let { task ->
//            task.checked = checked
//        }
//    }
//
//    fun remove(item: WellnessTask) {
//        _tasks.remove(item)
//    }
}

