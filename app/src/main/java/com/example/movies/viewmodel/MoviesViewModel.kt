package com.example.movies.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.example.movies.common.Intent
import com.example.movies.common.IntentFactory
import com.example.movies.model.MovieModelStore
import com.example.movies.model.MovieResponseDto
import com.example.movies.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel(), IntentFactory<MovieIntents> {

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
            override fun reduce(oldState: MovieState): Flow<MovieState> =
                flow {
                    emit(oldState.copy(syncState = MovieSyncState.MovieLoading))
                    repository
                        .fetchMovies()
                        .catch { emit(oldState.copy(syncState = MovieSyncState.MovieFailure(it.stackTraceToString()))) }
                        .collect { value: List<MovieResponseDto> ->
                            emit(
                                oldState.copy(
                                    movies = value,
                                    syncState = MovieSyncState.MovieSuccess
                                )
                            )
                        }
                }.catch {
                    emit(oldState.copy(syncState = MovieSyncState.MovieFailure("Something went wrong!")))
                }
        }
    }
}