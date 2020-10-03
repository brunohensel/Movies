package com.example.movies.repository

import com.example.movies.model.MovieResponseDto
import retrofit2.http.GET

interface MoviesApi {

    @GET("/shows")
    suspend fun fetchMovies(): List<MovieResponseDto>
}