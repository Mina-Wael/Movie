package com.idyllic.movie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.domain.repository.RepositoryIntr
import com.idyllic.movie.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(private val repo: RepositoryIntr) : ViewModel() {

    private val _movieStateFlow: MutableStateFlow<Resource<MoviePojoResult>> =
        MutableStateFlow(Resource.Loading)
    val movieStateFlow: StateFlow<Resource<MoviePojoResult>> = _movieStateFlow

    fun getTopRatedMovies() {
        viewModelScope.launch {
            repo.getTopRatedMovies().onEach {
                _movieStateFlow.emit(it)
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            repo.search(query, 1)
        }
    }
}