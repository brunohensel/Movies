package com.example.movies.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.common.Intent
import com.example.movies.common.IntentFactory
import com.example.movies.model.MovieModelStore
import com.example.movies.model.MovieResponseDto
import com.example.movies.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel(), IntentFactory<MovieIntents> {

/*    val state: LiveData<MovieState> =
        liveData {
            repository
                .fetchMovies()
                .onStart { emit(MovieLoading) }
                .catch { error -> emit(MovieFailure(error.stackTraceToString())) }
                .collect { movies -> emit(MovieSuccess(movies)) }
        }*/

    override suspend fun process(viewEvent: MovieIntents) {
        MovieModelStore.process(toState(viewEvent))
    }

    private fun toState(intents: MovieIntents): Intent<MovieState> {
        return when (intents) {
            MovieIntents.FetchMovies -> fetchMovie()
        }
    }

    private fun fetchMovie(): Intent<MovieState> {
        return object : Intent<MovieState> {
            override fun reduce(oldState: MovieState): MovieState {

                fun onError(error: Throwable): MovieState {
                    return oldState.copy(syncState = MovieSyncState.MovieFailure(error.stackTraceToString()))
                }

                fun onSuccess(movies: List<MovieResponseDto>): MovieState {
                    return oldState.copy(movies = movies, syncState = MovieSyncState.MovieSuccess)
                }

                viewModelScope.launch {
                    repository
                        .fetchMovies()
                        .onStart { oldState.copy(syncState = MovieSyncState.MovieLoading) }
                        .catch { error -> onError(error) }
                        .collect { movies -> onSuccess(movies) }
                }
                return oldState.copy(syncState = MovieSyncState.MovieIdle)
            }
        }
    }
}