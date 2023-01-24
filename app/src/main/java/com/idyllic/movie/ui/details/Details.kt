package com.idyllic.movie.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.idyllic.movie.R
import com.idyllic.movie.databinding.FragmentDetailsBinding
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.utils.Constants

class Details : Fragment() {

    val viewModel: DetailsViewModel by viewModels()

    val args: DetailsArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataToUi(args.movie)
    }

    private fun setDataToUi(movie: Movie) {
        Glide.with(requireContext()).load(Constants.IMAGE_URL + movie.poster_path)
            .into(binding.movieImage)
        binding.movieDescription.text = movie.overview
        binding.ratingBar.rating = movie.vote_average.toFloat() / 2

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}