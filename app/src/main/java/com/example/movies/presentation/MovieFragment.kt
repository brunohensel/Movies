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
import com.example.movies.presentation.adapter.BestMoviesAdapter
import com.example.movies.presentation.adapter.MoviesCategoriesAdapter
import com.example.movies.reduce.ViewEventFlow
import com.example.movies.util.MovieGenreMock
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_movie.*
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
    private val bestMovieAdapter: BestMoviesAdapter by lazy { BestMoviesAdapter() }
    private val categoryMovieAdapter: MoviesCategoriesAdapter by lazy { MoviesCategoriesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MovieModelStore
            .modelState()
            .map { movieState ->
                when (movieState.syncState) {
                    MovieSyncState.MovieLoading -> displayLoading(isLoading = true)
                    MovieSyncState.MovieSuccess -> displayMovies(movieState.movies)
                    is MovieSyncState.MovieFailure -> displayError(movieState.syncState)
                }
            }
            .launchIn(scope)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvBestMovies.adapter = bestMovieAdapter
        rvCategories.adapter = categoryMovieAdapter

        viewEvents()
            .onEach { intents -> viewModel.process(intents) }
            .launchIn(scope)
    }

    private fun displayError(message: MovieSyncState.MovieFailure) {
        displayLoading(isLoading = false)
        Log.e("Error", message.exception.message ?: "Unknown error")
    }

    private fun displayLoading(isLoading: Boolean) {
        Log.w("Loading", "$isLoading")
    }

    private fun displayMovies(movies: List<MovieResponseDto>) {
        displayLoading(isLoading = false)
        bestMovieAdapter.submitList(movies)
        categoryMovieAdapter.submitList(MovieGenreMock.arrayList)
    }

    override fun viewEvents(): Flow<ViewIntents> {
        val flows = listOf(
            ViewIntents.FetchMovies
        )
        return flows.asFlow()
    }
}