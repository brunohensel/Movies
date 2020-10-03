package com.example.movies.viewmodel

import com.example.movies.model.MovieResponseDto

sealed class MovieState
data class MovieSuccess(val movies: List<MovieResponseDto>) : MovieState()
data class MovieFailure(val message: String) : MovieState()
object MovieLoading : MovieState()
