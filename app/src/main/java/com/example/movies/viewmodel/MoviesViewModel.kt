package com.example.movies.viewmodel

import androidx.lifecycle.ViewModel
import com.example.movies.repository.MoviesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository): ViewModel() {
}