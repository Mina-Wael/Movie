package com.idyllic.movie.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.idyllic.movie.databinding.FragmentHomeBinding
import com.idyllic.movie.utils.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class Home : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var _viewPagerAdapter: ViewPagerAdapter? = null
    private val viewPagerAdapter get() = _viewPagerAdapter!!

    private var _mainRecyclerAdapter: MainRecycleAdapter? = null
    private val mainRecyclerAdapter get() = _mainRecyclerAdapter!!

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewPager()
        setUpMainRecycler()

        viewModel.getTopRatedMovie()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieStateFlow.collect {

                    when (it) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            viewPagerAdapter.setList(it.data.results.slice(10..15))
                        }
                        is Resource.Fail -> {}
                    }

                }
            }

        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flow.collectLatest {
                    mainRecyclerAdapter.submitData(it)
                }

                mainRecyclerAdapter.loadStateFlow.collect() {
                    val state = it.refresh
                    binding.progress.isVisible = state is LoadState.Loading

                }
            }
        }


    }


    private fun setUpMainRecycler() {
        _mainRecyclerAdapter = MainRecycleAdapter(MovieDiffUtil)
        binding.mainRecycler.apply {
            adapter = mainRecyclerAdapter
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
    }

    private fun setupViewPager() {
        _viewPagerAdapter = ViewPagerAdapter()
        binding.viewPager.apply {
            adapter = viewPagerAdapter
        }
        binding.viewPagerDots.attachTo(binding.viewPager)
    }

}