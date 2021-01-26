package com.example.movies.presentation.moviesFragment.movieAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.domain.MovieDto
import kotlinx.android.synthetic.main.item_best_movies.view.*

class MoviesAdapter(private val click: (MovieDto) -> Unit) :
    ListAdapter<MovieDto, MoviesAdapter.MoviesViewHolder>(MoviesAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bind(getItem(position), click)
    }

    class MoviesViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: MovieDto, openDetail: ((MovieDto) -> Unit)) {
            with(itemView) {
                ctnBestMovies.setOnClickListener { openDetail.invoke(data) }
                tvBestMovieName.text = data.name
            }
        }

        companion object {
            fun from(parent: ViewGroup): MoviesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_best_movies, parent, false)
                return MoviesViewHolder(view)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<MovieDto>() {

        override fun areItemsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieDto, newItem: MovieDto): Boolean {
            return oldItem == newItem
        }
    }
}