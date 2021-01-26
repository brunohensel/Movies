package com.example.movies.domain

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

/**Data class to be used to serialized the response from HTTP call*/
@Keep
@Parcelize
data class MovieDto(
    val id: Int,
    val image: Image,
    val language: String,
    val name: String,
    val genres: List<String>,
    val summary: String,
    val rating: MRating
) : Parcelable

@Keep
@Parcelize
data class MRating(
    val average: Double
) : Parcelable

@Keep
@Parcelize
data class Image(
    val original: String
) : Parcelable