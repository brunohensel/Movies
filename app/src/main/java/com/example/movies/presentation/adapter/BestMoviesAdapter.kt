package com.example.movies.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.domain.MovieResponseDto
import kotlinx.android.synthetic.main.item_best_movies.view.*

class BestMoviesAdapter :
    ListAdapter<MovieResponseDto, BestMoviesAdapter.BestMoviesViewHolder>(BestMoviesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestMoviesViewHolder {
        return BestMoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_best_movies, parent, false)
        )
    }

    override fun onBindViewHolder(holderBest: BestMoviesViewHolder, position: Int) {
        holderBest.bind(getItem(position))
    }

    inner class BestMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("UnsafeExperimentalUsageError")
        fun bind(data: MovieResponseDto) {
            with(itemView) {

                tvBestMovieName.text = data.name

                Glide
                    .with(this)
                    .load(data.image.original)
                    .circleCrop()
                    .into(imgBestMovie)
            }

        }
    }
}