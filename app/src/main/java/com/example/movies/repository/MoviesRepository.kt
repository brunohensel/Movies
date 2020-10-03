package com.example.movies.repository

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val api: MoviesApi) {

    fun fetchMovies() =
        flow {
            api
                .fetchMovies()
                .map { emit(listOf(it)) }
        }.flowOn(IO)
}