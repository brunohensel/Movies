package com.example.movies.presentation.detailsFragment

sealed class DetailViewIntents {
    data class FetchDetailMovie(val id: Int) : DetailViewIntents()
}