package com.idyllic.movie.data.remotesource

import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String = Constants.API_KEY): Response<MoviePojoResult>

    @GET("search/movie")
    suspend fun search(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("query") movieName: String, page: Int = 1
    ): Response<MoviePojoResult>

    @GET("trending/movie/day")
    suspend fun getTrendingMovies(
        @Query("api_key") apiKey: String = Constants.API_KEY,
        @Query("page") page: Int = 1
    ): Response<MoviePojoResult>
}