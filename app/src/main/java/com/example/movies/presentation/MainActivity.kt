package com.example.movies.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.movies.R
import com.example.movies.model.MovieResponseDto
import com.example.movies.presentation.viewmodel.MovieIntents
import com.example.movies.presentation.viewmodel.MoviesViewModel
import com.example.movies.repository.MovieState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.setStateIntent(MovieIntents.FetchMovies)
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