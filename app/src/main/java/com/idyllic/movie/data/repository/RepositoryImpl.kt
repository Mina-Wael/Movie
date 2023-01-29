package com.idyllic.movie.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.idyllic.movie.data.localsource.MovieDao
import com.idyllic.movie.data.remotesource.MovieApi
import com.idyllic.movie.data.remotesource.SearchPagingSource
import com.idyllic.movie.domain.model.Movie
import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.domain.model.MovieTable
import com.idyllic.movie.domain.repository.RepositoryIntr
import com.idyllic.movie.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: MovieApi, private val dao: MovieDao) :
    RepositoryIntr {

    override fun getTopRatedMovies(): Flow<Resource<MoviePojoResult>> = flow {
        emit(Resource.Loading)
        try {
            val res = api.getTopRatedMovies(page = 2)
            if (res.isSuccessful)
                emit(Resource.Success(res.body()!!))
            else
                emit(Resource.Fail("dasd"))

        } catch (ex: HttpException) {
            emit(Resource.Fail("Un expected error"))
        } catch (ex: IOException) {
            emit(Resource.Fail("Check your network connection"))
        }
    }

    override suspend fun search(query: String, page: Int): Flow<Resource<MoviePojoResult>> = flow {
        emit(Resource.Loading)
        try {
            val res = api.search(movieName = query, page = page)
            if (res.isSuccessful)
                emit(Resource.Success(res.body()!!))
            else
                emit(Resource.Fail("dasd"))

        } catch (ex: HttpException) {
            emit(Resource.Fail("Un expected error"))
        } catch (ex: IOException) {
            emit(Resource.Fail("Check your network connection"))
        }
    }

    override fun getSearchResult(query: String): LiveData<PagingData<Movie>> = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(pageSize = 20)
    ) {
        SearchPagingSource(query, api)
    }.liveData

    override suspend fun addMovie(movie: MovieTable) {
        dao.addMovie(movie)
    }

    override suspend fun remove(movie: MovieTable) {
        dao.remove(movie)
    }

    override suspend fun getAllMovies(): List<MovieTable> {
        return dao.getAllMovies()
    }
}