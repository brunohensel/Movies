package com.example.movies.repository

import com.example.movies.model.MovieResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepository @Inject constructor(private val api: MoviesApi) {

    suspend fun fetchMovies() =
        flow<List<MovieResponseDto>> {
            api
                .fetchMovies()
        }.flowOn(Dispatchers.IO)


}