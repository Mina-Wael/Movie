package com.idyllic.movie.data.repository

import android.util.Log
import com.idyllic.movie.data.remotesource.MovieApi
import com.idyllic.movie.domain.model.TopRatedMovie
import com.idyllic.movie.domain.repository.RepositoryIntr
import javax.inject.Inject

class RepositoryImpl @Inject constructor(val api: MovieApi) : RepositoryIntr {

    override suspend fun getTopRatedMovies(): TopRatedMovie {
        Log.i("TAG", "getTopRatedMovies: "+api.getTopRatedMovies().body())
        return api.getTopRatedMovies().body()!!
    }
}