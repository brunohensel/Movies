package com.example.movies.presentation.categoryFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.domain.MovieDto
import com.example.movies.presentation.categoryFragment.CategoryAdapter.CategoryAdapterViewHolder
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter : ListAdapter<MovieDto, CategoryAdapterViewHolder>(CategoryAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        return CategoryAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: MovieDto) {
            with(itemView) {
                tvMovieTitle.text = data.name

                Glide
                    .with(this)
                    .load(data.image.original)
                    .into(imgBestMovie)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<MovieDto>() {
        override fun areItemsTheSame(
            oldItem: MovieDto,
            newItem: MovieDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieDto,
            newItem: MovieDto
        ): Boolean {
            return oldItem == newItem
        }
    }
}