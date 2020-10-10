package com.example.movies.domain.detail

import com.example.movies.domain.movie.CacheMovieDto
import com.example.movies.domain.movie.MovieSyncState

data class DetailState(
    val movie: CacheMovieDto,
    val syncState: MovieSyncState
)