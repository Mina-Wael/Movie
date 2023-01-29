package com.idyllic.movie.ui.saved

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.idyllic.movie.R
import com.idyllic.movie.databinding.DeleteDialogBinding
import com.idyllic.movie.databinding.FragmentSavedBinding
import com.idyllic.movie.domain.model.MovieTable
import com.idyllic.movie.utils.Common.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Saved : Fragment() {


    private val viewModel: SavedViewModel by viewModels()

    private var _binding: FragmentSavedBinding? = null
    private val binding get() = _binding!!

    private val dialog: Dialog by lazy {
        Dialog(requireContext())
    }


    private var movieAdapter: SavedRecyclerAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpRecyclerView()
        viewModel.getAllMovies()
        listenToMovieStateFlow()


    }

    private fun showDeleteDialog(movie: MovieTable) {
        var dialogBinding: DeleteDialogBinding =
            DeleteDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        dialog.setContentView(dialogBinding.root)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.show()

        dialogBinding.btnDelete.setOnClickListener {
            showSnackBar(binding.root, "Deleted")
            viewModel.deleteMovie(movie)
            viewModel.getAllMovies()
            dialog.dismiss()
        }
        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

    }

    private fun listenToMovieStateFlow() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieStateFlow.collectLatest {
                    movieAdapter!!.setList(it)
                }
            }
        }
    }

    private fun setUpRecyclerView() {
        movieAdapter = SavedRecyclerAdapter(MovieTableDiffUtil, onDeleteClick)
        binding.recycler.apply {
            adapter = movieAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val onDeleteClick = fun(movie: MovieTable) {
        showDeleteDialog(movie)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieAdapter = null
        _binding = null
    }

}