package com.example.movies.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import com.example.movies.R
import com.example.movies.data.repository.MovieState
import com.example.movies.domain.MovieResponseDto
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

    override fun onSupportNavigateUp() =
        findNavController(this, R.id.nav_host_fragment).navigateUp()

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