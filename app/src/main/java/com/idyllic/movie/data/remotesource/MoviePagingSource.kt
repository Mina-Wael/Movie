package com.idyllic.movie.data.remotesource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.idyllic.movie.domain.model.Movie


class MoviePagingSource(private val remoteSource: MovieApi) :
    PagingSource<Int, Movie>() {
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        var nextPage = params.key ?: 1
        Log.i("TAG", "load: $nextPage")
        val response = remoteSource.getTopRatedMovies(page = nextPage)
        return LoadResult.Page(response.body()!!.results, null, nextPage + 1)
    }
}