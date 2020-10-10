package com.example.movies.presentation.moviesFragment.movieAdapter

import androidx.recyclerview.widget.DiffUtil
import com.example.movies.domain.MovieResponseDto

class BestMoviesDiff : DiffUtil.ItemCallback<MovieResponseDto>() {

    override fun areItemsTheSame(oldItem: MovieResponseDto, newItem: MovieResponseDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieResponseDto, newItem: MovieResponseDto): Boolean {
        return oldItem == newItem
    }
}
