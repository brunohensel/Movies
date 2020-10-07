package com.example.movies.model

import androidx.annotation.Keep
/**Data class to be used to serialized the response from HTTP call*/
@Keep
data class MovieResponseDto(
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
)
@Keep
data class Image(
    val original: String
)