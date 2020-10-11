package com.example.movies.presentation.moviesFragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movies.R
import com.example.movies.domain.MovieResponseDto
import com.example.movies.domain.movie.MovieModelStore
import com.example.movies.domain.movie.MovieSyncState
import com.example.movies.presentation.detailsFragment.DetailsFragment
import com.example.movies.presentation.moviesFragment.movieAdapter.BestMoviesAdapter
import com.example.movies.presentation.moviesFragment.movieAdapter.MoviesCategoriesAdapter
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
class MovieFragment : Fragment(R.layout.fragment_movie), ViewEventFlow<MovieViewIntents> {

    private val viewModel: MoviesViewModel by viewModels()
    private val scope: CoroutineScope = MainScope()
    private val bestMovieAdapter: BestMoviesAdapter by lazy { BestMoviesAdapter(::onBestMovieClick) }
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

    private fun onBestMovieClick(data: MovieResponseDto) {
        val id = data.id
        findNavController().navigate(
            R.id.action_moviesFragment_to_detailsFragment,
            bundleOf(DetailsFragment.MOVIE_DETAIL to id)
        )
    }

    override fun viewEvents(): Flow<MovieViewIntents> {
        val flows = listOf(
            MovieViewIntents.FetchMovies
        )
        return flows.asFlow()
    }
}