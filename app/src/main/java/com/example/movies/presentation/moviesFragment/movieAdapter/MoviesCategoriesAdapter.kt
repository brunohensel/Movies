package com.example.movies.presentation.moviesFragment.movieAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.movies.R
import com.example.movies.presentation.moviesFragment.movieAdapter.MoviesCategoriesAdapter.MovieCategoriesViewHolder
import kotlinx.android.synthetic.main.item_categories.view.*

class MoviesCategoriesAdapter :
    ListAdapter<String, MovieCategoriesViewHolder>(MoviesCategoriesDiff()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieCategoriesViewHolder {
        return MovieCategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_categories, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieCategoriesViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieCategoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(type: String) {
            with(itemView) {
             tvMovieCategory.text = type
            }
        }
    }
}