package com.example.movies.domain.movie

import com.example.movies.domain.MovieDto

data class MovieState(
    val movies: List<MovieDto>,
    val syncState: MovieSyncState
)

sealed class MovieSyncState {
    object MovieLoading : MovieSyncState()
    object MovieIdle : MovieSyncState()
    object MovieSuccess : MovieSyncState()
    data class MovieFailure(val exception: Exception) : MovieSyncState()
}

