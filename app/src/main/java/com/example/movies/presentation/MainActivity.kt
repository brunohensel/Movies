package com.example.movies.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.movies.R
import com.example.movies.viewmodel.MovieFailure
import com.example.movies.viewmodel.MovieLoading
import com.example.movies.viewmodel.MovieSuccess
import com.example.movies.viewmodel.MoviesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.state.observe(this, Observer { movieState ->
            when (movieState) {
                is MovieLoading -> Log.w("Loading", "Loading")
                is MovieSuccess -> Log.w("Success", "${movieState.movies}")
                is MovieFailure -> Log.e("Error", movieState.message)
            }
        })
    }
}