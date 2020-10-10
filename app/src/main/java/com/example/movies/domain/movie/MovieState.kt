package com.example.movies.domain.movie

import com.example.movies.domain.MovieResponseDto

data class MovieState(
    val movies: List<MovieResponseDto>,
    val syncState: MovieSyncState
)

sealed class MovieSyncState {
    object MovieLoading : MovieSyncState()
    object MovieIdle : MovieSyncState()
    object MovieSuccess : MovieSyncState()
    data class MovieFailure(val exception: Exception) : MovieSyncState()
}

