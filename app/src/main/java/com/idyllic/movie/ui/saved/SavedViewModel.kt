package com.idyllic.movie.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idyllic.movie.domain.model.MovieTable
import com.idyllic.movie.domain.repository.RepositoryIntr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(private val repo: RepositoryIntr) : ViewModel() {

    private val _movieStateFlow = MutableStateFlow<List<MovieTable>>(emptyList())
    val movieStateFlow: StateFlow<List<MovieTable>> = _movieStateFlow

    fun getAllMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieStateFlow.emit(repo.getAllMovies())
        }
    }

    fun deleteMovie(movieTable: MovieTable) {
        viewModelScope.launch {
            repo.remove(movieTable)
        }
    }

}