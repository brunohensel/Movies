package com.example.movies.domain.category

import com.example.movies.domain.MovieResponseDto
import com.example.movies.domain.movie.MovieSyncState

data class CategoryState(
    val movies: List<MovieResponseDto>,
    val syncState: MovieSyncState
)