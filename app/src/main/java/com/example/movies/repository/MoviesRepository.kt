package com.example.movies.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val api: MoviesApi) {

    suspend fun fetchMovies() =
        api
            .fetchMovies()
            .flowOn(Dispatchers.IO)
}