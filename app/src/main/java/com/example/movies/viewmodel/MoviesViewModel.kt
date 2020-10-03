package com.example.movies.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.movies.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    val state: LiveData<MovieState> = liveData(Main) {
        repository
            .fetchMovies()
            .onStart { emit(MovieLoading) }
            .catch { error -> emit(MovieFailure(error.stackTraceToString())) }
            .collect { movies -> emit(MovieSuccess(movies)) }
    }
}