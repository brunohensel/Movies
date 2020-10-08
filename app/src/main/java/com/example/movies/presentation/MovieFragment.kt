package com.example.movies.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.data.repository.MovieSyncState
import com.example.movies.domain.MovieModelStore
import com.example.movies.domain.MovieResponseDto
import com.example.movies.reduce.ViewEventFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), ViewEventFlow<ViewIntents> {

    private val viewModel: MoviesViewModel by viewModels()
    private val scope: CoroutineScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MovieModelStore
            .modelState()
            .map { movieState ->
                when (movieState.syncState) {
                    MovieSyncState.MovieLoading -> displayLoading(isLoading = true)
                    MovieSyncState.MovieSuccess -> dislpayMovies(movieState.movies)
                    is MovieSyncState.MovieFailure -> displayError(movieState.syncState.exception.message)
                }
            }
            .launchIn(scope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewEvents()
            .onEach { intents -> viewModel.process(intents) }
            .launchIn(scope)
    }

    private fun displayError(message: String?) {
        displayLoading(isLoading = false)
        Log.e("Error", message ?: "Unknown error")
    }

    private fun displayLoading(isLoading: Boolean) {
        Log.w("Loading", "$isLoading")
    }

    private fun dislpayMovies(movies: List<MovieResponseDto>) {
        displayLoading(isLoading = false)
        Log.w("Success", "$movies")
    }

    override fun viewEvents(): Flow<ViewIntents> {
        val flows = listOf(
            ViewIntents.FetchMovies
        )
        return flows.asFlow()
    }
}