package com.example.movies.data.repository

sealed class MovieState<out R> {
    data class MovieSuccess<out T>(val data: T) : MovieState<T>()
    data class MovieFailure(val exception: Exception) : MovieState<Nothing>()
    object MovieLoading : MovieState<Nothing>()
}

