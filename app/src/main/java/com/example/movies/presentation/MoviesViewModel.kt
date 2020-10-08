package com.example.movies.presentation

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.movies.data.repository.MovieState
import com.example.movies.data.repository.MoviesRepository
import com.example.movies.domain.MovieModelStore
import com.example.movies.reduce.Intent
import com.example.movies.reduce.IntentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(), IntentFactory<ViewIntents> {

    override suspend fun process(viewEvent: ViewIntents) {
        MovieModelStore.process(toState(viewEvent))
    }

    private fun toState(intents: ViewIntents): Intent<MovieState> {
        return when (intents) {
            ViewIntents.FetchMovies -> fetchMovie()
        }
    }

    private fun fetchMovie(): Intent<MovieState> = repository.fetchMovie()
}
