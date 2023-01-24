package com.idyllic.movie.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idyllic.movie.databinding.HomeRecyclerRowBinding
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.utils.Constants

class MainRecycleAdapter(
    private val movieDiffUtil: DiffUtil.ItemCallback<Movie>,
    private val onItemClick: (Movie) -> Unit
) :
    PagingDataAdapter<Movie, MainRecycleAdapter.MainViewHolder>(movieDiffUtil) {


    inner class MainViewHolder(
        private val binding: HomeRecyclerRowBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(movie: Movie) {
            binding.rvText.text = movie.title
            Glide.with(context).load(Constants.IMAGE_URL + movie.poster_path).into(binding.rvImage)
        }

        fun onClick(movie: Movie) {
            binding.root.setOnClickListener {
                onItemClick(movie)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding =
            HomeRecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MainViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindData(getItem(position)!!)
        holder.onClick(getItem(position)!!)
    }


}