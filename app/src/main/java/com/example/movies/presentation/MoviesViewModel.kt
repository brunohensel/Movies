package com.example.movies.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.movies.data.repository.MovieState
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.MovieResponseDto
import com.example.movies.presentation.MovieIntents.FetchMovies
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    
    private val _movieState: MutableLiveData<MovieState<List<MovieResponseDto>>> = MutableLiveData()
    val movieState: LiveData<MovieState<List<MovieResponseDto>>>
        get() = _movieState

    fun setStateIntent(intents: MovieIntents) {
        viewModelScope.launch {
            when (intents) {
                is FetchMovies -> {
                    repository.fetchMovies()
                        .onEach { movieState ->
                            _movieState.value = movieState
                        }
                        .launchIn(viewModelScope)
                }
            }
        }
    }
}

sealed class MovieIntents {
    object FetchMovies : MovieIntents()
}