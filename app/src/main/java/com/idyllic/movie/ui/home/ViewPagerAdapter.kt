package com.idyllic.movie.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.idyllic.movie.databinding.ViewPagerRowBinding
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.utils.Constants.IMAGE_URL

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerHolder>() {

    private var movies: List<Movie> = emptyList()

    inner class ViewPagerHolder(
        private val binding: ViewPagerRowBinding,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(movie: Movie) {
            // binding.title.text = movie.title
            Glide.with(context).load(IMAGE_URL + movie.poster_path)
                .placeholder(CircularProgressDrawable(context))
                .into(binding.image)
            binding.tvRate.text = movie.vote_average.toString()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        val binding =
            ViewPagerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewPagerHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.setData(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(movieList: List<Movie>) {
        movies = movieList
        notifyDataSetChanged()
    }
}