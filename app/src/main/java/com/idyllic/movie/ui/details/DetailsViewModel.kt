package com.idyllic.movie.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.idyllic.movie.domain.model.MovieTable
import com.idyllic.movie.domain.repository.RepositoryIntr
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: RepositoryIntr) : ViewModel() {

    fun saveMovie(movieTable: MovieTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addMovie(movieTable)
        }
    }

    fun deleteMovie(movieTable: MovieTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.remove(movieTable)
        }
    }

}