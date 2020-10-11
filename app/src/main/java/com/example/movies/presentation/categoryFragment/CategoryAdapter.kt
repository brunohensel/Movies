package com.example.movies.presentation.categoryFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movies.R
import com.example.movies.domain.MovieResponseDto
import com.example.movies.presentation.categoryFragment.CategoryAdapter.CategoryAdapterViewHolder
import kotlinx.android.synthetic.main.item_category.view.*

class CategoryAdapter : ListAdapter<MovieResponseDto, CategoryAdapterViewHolder>(CategoryAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapterViewHolder {
        return CategoryAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CategoryAdapterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: MovieResponseDto) {
            with(itemView) {
                tvMovieTitle.text = data.name

                Glide
                    .with(this)
                    .load(data.image.original)
                    .into(imgBestMovie)
            }
        }
    }

    private companion object : DiffUtil.ItemCallback<MovieResponseDto>() {
        override fun areItemsTheSame(
            oldItem: MovieResponseDto,
            newItem: MovieResponseDto
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: MovieResponseDto,
            newItem: MovieResponseDto
        ): Boolean {
            return oldItem == newItem
        }
    }
}