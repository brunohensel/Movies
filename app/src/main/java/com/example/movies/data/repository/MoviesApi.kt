package com.example.movies.data.repository

import com.example.movies.domain.MovieResponseDto
import retrofit2.http.GET

interface MoviesApi {

    @GET("/shows")
    suspend fun fetchMovies(): List<MovieResponseDto>
}