package com.idyllic.movie.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.idyllic.movie.databinding.FragmentHomeBinding
import com.idyllic.movie.domain.model.Movie
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

    private var _searchAdapter: MainRecycleAdapter? = null
    private val searchAdapter get() = _searchAdapter!!

    private val viewModel: HomeViewModel by viewModels()

    var isTextNull = true


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
        setupSearch()

        _searchAdapter = MainRecycleAdapter(MovieDiffUtil, onItemClick)

        viewModel.getTopRatedMovie()

        listenToTopRatedMovies()

        binding.searchView.setOnQueryTextFocusChangeListener { p0, p1 ->
            if (p1) {
                hideViewPager()
                Log.i("TAG", "hide: ")
            } else {
                if (isTextNull) {
                    showViewPager()
                    Log.i("TAG", "show: ")
                    binding.mainRecycler.adapter = mainRecyclerAdapter
                    searchAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
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

        viewModel.searchFlow.observe(viewLifecycleOwner, Observer {
            searchAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })


    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.search(newText)
                    isTextNull = false
                    if (binding.mainRecycler.adapter == mainRecyclerAdapter)
                        binding.mainRecycler.adapter = searchAdapter
                } else {
                    isTextNull = true
                    showViewPager()
                    binding.mainRecycler.adapter = mainRecyclerAdapter
                    searchAdapter.submitData(viewLifecycleOwner.lifecycle, PagingData.empty())
                }
                return true
            }
        })
    }

    private fun listenToTopRatedMovies() {
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
    }


    private fun setUpMainRecycler() {
        _mainRecyclerAdapter = MainRecycleAdapter(MovieDiffUtil, onItemClick)
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

    private fun hideViewPager() {
        binding.viewPager.visibility = View.GONE
        binding.viewPagerDots.visibility = View.GONE
    }

    private fun showViewPager() {
        binding.viewPager.visibility = View.VISIBLE
        binding.viewPagerDots.visibility = View.VISIBLE
    }

    private val onItemClick = fun(movie: Movie) {
        findNavController().navigate(HomeDirections.actionHome2ToDetails(movie))
    }

}