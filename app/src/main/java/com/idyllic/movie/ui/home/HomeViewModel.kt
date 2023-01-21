package com.idyllic.movie.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.idyllic.movie.data.remotesource.MovieApi
import com.idyllic.movie.data.remotesource.MoviePagingSource
import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.domain.repository.RepositoryIntr
import com.idyllic.movie.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: RepositoryIntr,
    private val api: MovieApi
) : ViewModel() {

    private val _movieStateFlow: MutableStateFlow<Resource<MoviePojoResult>> =
        MutableStateFlow(Resource.Loading)
    val movieStateFlow: StateFlow<Resource<MoviePojoResult>> = _movieStateFlow

    fun getTopRatedMovie() {
        repo.getTopRatedMovies().onEach {
            _movieStateFlow.emit(it)
        }.launchIn(viewModelScope)

    }

    fun search(query: String) {

    }

    val flow = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        MoviePagingSource(api)
    }.flow.cachedIn(viewModelScope)
}