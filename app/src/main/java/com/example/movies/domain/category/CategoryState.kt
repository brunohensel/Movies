package com.example.movies.domain.category

import com.example.movies.domain.MovieDto
import com.example.movies.domain.movie.MovieSyncState

data class CategoryState(
    val movies: List<MovieDto>,
    val syncState: MovieSyncState
)