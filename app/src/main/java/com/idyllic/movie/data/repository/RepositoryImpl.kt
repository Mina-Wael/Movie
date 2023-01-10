package com.idyllic.movie.data.repository

import com.idyllic.movie.data.remotesource.MovieApi
import com.idyllic.movie.domain.model.MoviePojoResult
import com.idyllic.movie.domain.repository.RepositoryIntr
import com.idyllic.movie.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val api: MovieApi) : RepositoryIntr {

    override suspend fun getTopRatedMovies(): Flow<Resource<MoviePojoResult>> = flow {
        emit(Resource.Loading)
        try {
            val res = api.getTopRatedMovies()
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
}