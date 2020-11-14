package com.example.movies.presentation.moviesFragment

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.movie.MovieModelStore
import com.example.movies.domain.movie.MovieState
import com.example.movies.reduce.Intent
import com.example.movies.reduce.IntentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), IntentFactory<MovieViewIntents> {

    override suspend fun process(viewEvent: MovieViewIntents) {
        MovieModelStore.process(toState(viewEvent))
    }

    private fun toState(intentsMovie: MovieViewIntents): Intent<MovieState> {
        return when (intentsMovie) {
            MovieViewIntents.FetchMovies -> fetchMovie()
        }
    }

    private fun fetchMovie(): Intent<MovieState> = repository.fetchMovie()
}
