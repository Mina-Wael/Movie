package com.idyllic.movie.ui.saved

import androidx.recyclerview.widget.DiffUtil
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.model.MovieTable

object MovieTableDiffUtil : DiffUtil.ItemCallback<MovieTable>() {
    override fun areItemsTheSame(oldItem: MovieTable, newItem: MovieTable): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieTable, newItem: MovieTable): Boolean {
        return oldItem == newItem
    }
}