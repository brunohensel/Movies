package com.example.movies.domain

import androidx.annotation.Keep

/**Data class to be used to serialized the response from HTTP call*/
@Keep
data class MovieResponseDto(
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
    val genres: List<String>,
    val summary: String,
    val rating: MRating
)

@Keep
data class MRating(
    val average: Double
)

@Keep
data class Image(
    val original: String
)