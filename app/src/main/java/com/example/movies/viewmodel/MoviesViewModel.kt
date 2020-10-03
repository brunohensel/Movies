package com.example.movies.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.repository.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Singleton
class MoviesViewModel @ViewModelInject constructor(
    private val repository: MoviesRepository
) : ViewModel() {

    private val _state = MutableLiveData<MovieState>()
    val state: LiveData<MovieState> get() = _state

    fun fetchMovies() =
        viewModelScope.launch {
            repository
                .fetchMovies()
                .onStart { MovieLoading }
                .catch { error -> MovieFailure(error.stackTraceToString()) }
                .collect { movies -> _state.value = MovieSuccess(movies) }
        }
}