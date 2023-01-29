package com.idyllic.movie.ui.home

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.idyllic.movie.data.remotesource.MovieApi
import com.idyllic.movie.data.remotesource.MoviePagingSource
import com.idyllic.movie.data.remotesource.SearchPagingSource
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.domain.repository.RepositoryIntr
import com.idyllic.movie.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: RepositoryIntr,
    private val api: MovieApi
) : ViewModel() {

    private val _movieStateFlow: MutableStateFlow<Resource<MoviePojoResult>> =
        MutableStateFlow(Resource.Loading)
    val movieStateFlow: StateFlow<Resource<MoviePojoResult>> = _movieStateFlow

    private val _searchLiveData = MutableLiveData<Resource<MoviePojoResult>>()
     val searchLiveData: LiveData<Resource<MoviePojoResult>> = _searchLiveData


    var searchJob: Job? = null


    fun getTopRatedMovie() {
        repo.getTopRatedMovies().onEach {
            _movieStateFlow.emit(it)
        }.launchIn(viewModelScope)

    }

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            repo.search(query, 1).collect {
                _searchLiveData.postValue(it)
            }
        }
    }

}