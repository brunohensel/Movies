package com.example.movies.model

data class MovieState(
    val movies: List<MovieResponseDto>,
    val syncState: MovieSyncState
)

sealed class MovieSyncState {
    object MovieLoading : MovieSyncState()
    object MovieIdle : MovieSyncState()
    object MovieSuccess : MovieSyncState()
    data class MovieFailure(val message: String) : MovieSyncState()
}


