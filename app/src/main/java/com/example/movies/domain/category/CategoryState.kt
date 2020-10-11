package com.example.movies.domain.category

import com.example.movies.domain.movie.CacheMovieDto
import com.example.movies.domain.movie.MovieSyncState

data class CategoryState(
    val movies: List<CacheMovieDto>,
    val syncState: MovieSyncState
)