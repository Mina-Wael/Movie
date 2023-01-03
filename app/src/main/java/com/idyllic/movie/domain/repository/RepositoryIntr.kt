package com.idyllic.movie.domain.repository

import com.idyllic.movie.domain.model.TopRatedMovie

interface RepositoryIntr {

    suspend fun getTopRatedMovies(): TopRatedMovie
}