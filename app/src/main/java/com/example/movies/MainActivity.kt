package com.example.movies

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movies.viewmodel.MovieFailure
import com.example.movies.viewmodel.MovieSuccess
import com.example.movies.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: MoviesViewModel by viewModels()

        viewModel.fetchMovies()

        viewModel.state.observe(this, Observer { movieState ->

          when (movieState) {
            is MovieSuccess -> Log.w("MainActivity", "${movieState.movies}")
            is MovieFailure -> Log.e("MainActivity", movieState.message)
          }
        })
    }
}