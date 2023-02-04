package com.idyllic.movie.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idyllic.movie.databinding.SearchRecyclerRowBinding
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.utils.Common
import com.idyllic.movie.utils.Constants

class SearchAdapter(
    private val diffUtil: DiffUtil.ItemCallback<Movie>,
    private val _onItemClick: (movie: Movie) -> Unit
) :
    RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    private val differList = AsyncListDiffer(this, diffUtil)

    inner class SearchHolder(
        private val binding: SearchRecyclerRowBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(movie: Movie) {
            binding.apply {
                Glide.with(context).load(Constants.IMAGE_URL + movie.poster_path)
                    .placeholder(Common.getProgressLoading(context))
                    .into(movieImage)
                tvRate.text = movie.vote_average.toString()
                tvRelease.text = movie.release_date
                tvTitle.text = movie.title
            }
        }

        fun onItemClick(movie: Movie) {
            binding.root.setOnClickListener {
                _onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding =
            SearchRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.setData(differList.currentList[position])
        holder.onItemClick(differList.currentList[position])
    }

    override fun getItemCount(): Int = differList.currentList.size


    fun setList(movies: List<Movie>) {
        differList.submitList(movies)
    }
}