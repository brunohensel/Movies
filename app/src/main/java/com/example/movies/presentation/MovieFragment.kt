package com.example.movies.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.movies.R
import com.example.movies.data.repository.MovieState
import com.example.movies.domain.MovieResponseDto
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.movieState.observe(this) { movieState ->
            when (movieState) {
                is MovieState.MovieSuccess<List<MovieResponseDto>> -> {
                    displayLoading(isLoading = false)
                    dislpayMovies(movies = movieState.data)
                }
                is MovieState.MovieFailure -> {
                    displayLoading(isLoading = false)
                    displayError(message = movieState.exception.message)
                }
                is MovieState.MovieLoading -> {
                    displayLoading(isLoading = true)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setStateIntent(MovieIntents.FetchMovies)
    }

    private fun displayError(message: String?) {
        Log.e("Error", message ?: "Unknown error")
    }

    private fun displayLoading(isLoading: Boolean) {
        Log.w("Loading", "$isLoading")
    }

    private fun dislpayMovies(movies: List<MovieResponseDto>) {
        Log.w("Success", "$movies")
    }
}