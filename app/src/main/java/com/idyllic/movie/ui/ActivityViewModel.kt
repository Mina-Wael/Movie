package com.idyllic.movie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.repository.RepositoryIntr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(val repo: RepositoryIntr) : ViewModel() {

    private val _movieStateFlow: MutableStateFlow<List<Movie>> = MutableStateFlow(emptyList())
    val movieStateFlow: StateFlow<List<Movie>> = _movieStateFlow

    fun getTopRatedMovies() {
        viewModelScope.launch {
            val res = repo.getTopRatedMovies()
            _movieStateFlow.emit(res.results)
        }
    }
}