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
import com.idyllic.movie.MainActivity
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

    private var _searchAdapter: SearchAdapter? = null
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
        setUpSearchRecycler()
        setupSearch()
        startListenToMainFlow()
        setPagingLoading()


        viewModel.getTopRatedMovie()
        listenToTopRatedMovies()
        startListenToSearchResult()


    }

    private fun setListenerToSearchView() {
        binding.searchView.setOnQueryTextFocusChangeListener { p0, p1 ->
            if (p1) {
                hideViewPager()
                hideMainRecycler()
                showSearchRecycler()
                (activity as MainActivity).hideBottomNavigation()
            } else {
                if (isTextNull) {
                    showViewPager()
                    showMainRecycler()
                    hideSearchRecycler()
                    searchAdapter.setList(emptyList())
                }
                (activity as MainActivity).showBottomNavigation()
            }
        }
    }


    private fun startListenToSearchResult() {
        viewModel.searchLiveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Loading -> {
                    binding.progress.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progress.visibility = View.GONE
                    searchAdapter.setList(it.data.results)
                }
                is Resource.Fail -> {}
            }
        })

    }

    private fun setupSearch() {
        setListenerToSearchView()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    viewModel.search(newText)

                } else {
                    // showViewPager()
                    searchAdapter.setList(emptyList())
                }
                isTextNull = newText.isNullOrEmpty()
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
                            viewPagerAdapter.setList(it.data.results.slice(5..10))
                        }
                        is Resource.Fail -> {}
                    }
                }
            }
        }
    }

    private fun startListenToMainFlow() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.flow.collect {
                    Log.i("TAG", "startListenToMainFlow: ")
                    mainRecyclerAdapter.submitData(it)
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

    private fun setUpSearchRecycler() {
        _searchAdapter = SearchAdapter(MovieDiffUtil, onItemClick)
        binding.searchRecycler.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupViewPager() {
        _viewPagerAdapter = ViewPagerAdapter(requireContext())
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

    private fun hideMainRecycler() {
        binding.mainRecycler.visibility = View.GONE
    }

    private fun showMainRecycler() {
        binding.mainRecycler.visibility = View.VISIBLE
    }

    private fun hideSearchRecycler() {
        binding.searchRecycler.visibility = View.GONE
    }

    private fun showSearchRecycler() {
        binding.searchRecycler.visibility = View.VISIBLE
    }

    private val onItemClick = fun(movie: Movie) {
        findNavController().navigate(HomeDirections.actionHome2ToDetails(movie))
    }

    private fun setPagingLoading() {
        lifecycleScope.launch {
            mainRecyclerAdapter.loadStateFlow.collectLatest { loadState ->
                binding.progress.isVisible = loadState.refresh is LoadState.Loading
            }
        }
    }

}