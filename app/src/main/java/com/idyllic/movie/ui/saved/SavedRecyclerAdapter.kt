package com.idyllic.movie.ui.saved

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.AsyncListUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idyllic.movie.databinding.FavoriteRecyclerRowBinding
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.model.MovieTable
import com.idyllic.movie.utils.Constants

class SavedRecyclerAdapter(
    private val diffUtil: DiffUtil.ItemCallback<MovieTable>,
    private val onItemDeleteClick: (MovieTable) -> Unit
) :
    RecyclerView.Adapter<SavedRecyclerAdapter.SavedHolder>() {

    private val movieList = AsyncListDiffer(this, diffUtil)

    inner class SavedHolder(
        private val binding: FavoriteRecyclerRowBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(movie: MovieTable) {
            Glide.with(context).load(Constants.IMAGE_URL + movie.poster_path).into(binding.image)
            binding.tvTitle.text = movie.title
        }

        fun onDeleteClick(movie: MovieTable) {
            binding.btnDelete.setOnClickListener {
                onItemDeleteClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedHolder {
        val binding =
            FavoriteRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SavedHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: SavedHolder, position: Int) {
        holder.setData(movieList.currentList[position])
        holder.onDeleteClick(movieList.currentList[position])
    }

    override fun getItemCount(): Int = movieList.currentList.size

    fun setList(movies: List<MovieTable>) {
        movieList.submitList(movies)
    }

}