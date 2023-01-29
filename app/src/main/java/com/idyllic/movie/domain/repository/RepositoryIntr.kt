package com.idyllic.movie.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.domain.model.MovieTable
import com.idyllic.movie.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RepositoryIntr {

    fun getTopRatedMovies(): Flow<Resource<MoviePojoResult>>

    suspend fun search(query: String, page: Int): Flow<Resource<MoviePojoResult>>

    fun getSearchResult(query: String): LiveData<PagingData<Movie>>

    suspend fun addMovie(movie: MovieTable)

    suspend fun remove(movie: MovieTable)

    suspend fun getAllMovies(): List<MovieTable>
}