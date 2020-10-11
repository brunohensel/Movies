package com.example.movies.presentation.categoryFragment

sealed class CategoryViewIntents {
    data class FetchMovieByCategory(val category: String) : CategoryViewIntents()
}