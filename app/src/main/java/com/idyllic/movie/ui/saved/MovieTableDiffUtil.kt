package com.idyllic.movie.ui.home

import androidx.recyclerview.widget.DiffUtil
import com.idyllic.movie.domain.model.Movie

object MovieDiffUtil : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}