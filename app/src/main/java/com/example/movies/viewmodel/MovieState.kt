package com.example.movies.viewmodel

import com.example.movies.model.MovieResponseDto

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


