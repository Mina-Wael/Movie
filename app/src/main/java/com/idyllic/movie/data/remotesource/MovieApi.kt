package com.idyllic.movie.data.remotesource

import com.idyllic.movie.domain.model.TopRatedMovie
import com.idyllic.movie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<TopRatedMovie>
}