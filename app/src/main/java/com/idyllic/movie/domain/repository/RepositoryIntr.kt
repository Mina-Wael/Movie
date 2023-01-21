package com.idyllic.movie.domain.repository

import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RepositoryIntr {

     fun getTopRatedMovies(): Flow<Resource<MoviePojoResult>>

    suspend fun search(query: String, page: Int): Flow<Resource<MoviePojoResult>>
}