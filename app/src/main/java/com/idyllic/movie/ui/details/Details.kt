package com.idyllic.movie.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.idyllic.movie.R
import com.idyllic.movie.databinding.FragmentDetailsBinding
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.model.toSavedMovie
import com.idyllic.movie.utils.Common.showSnackBar
import com.idyllic.movie.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Details : Fragment() {

    private val viewModel: DetailsViewModel by viewModels()

    private val args: DetailsArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var isPressed = false

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
        binding.btnSave.setOnClickListener {
            if (isPressed) {
                viewModel.deleteMovie(args.movie.toSavedMovie())
                binding.btnSave.setImageResource(R.drawable.favorite_border)
            } else {
                binding.btnSave.setImageResource(R.drawable.favorite_icon)
                viewModel.saveMovie(args.movie.toSavedMovie())
                showSnackBar(binding.root, "Saved")
            }
            isPressed = !isPressed
        }
    }

    private fun setDataToUi(movie: Movie) {
        Glide.with(requireContext()).load(Constants.IMAGE_URL + movie.poster_path)
            .into(binding.movieImage)
        binding.movieDescription.text = movie.overview
        binding.tvRate.text = movie.vote_average.toString()
        binding.tvDate.text = movie.release_date

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}