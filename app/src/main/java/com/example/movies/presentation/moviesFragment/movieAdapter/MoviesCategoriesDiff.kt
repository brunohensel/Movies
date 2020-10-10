package com.example.movies.presentation.moviesFragment.movieAdapter

import androidx.recyclerview.widget.DiffUtil

class MoviesCategoriesDiff : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}