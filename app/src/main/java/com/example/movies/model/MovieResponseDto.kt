package com.example.movies.model

data class MovieResponseDto(
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
)

data class Image(
    val medium: String,
    val original: String
)