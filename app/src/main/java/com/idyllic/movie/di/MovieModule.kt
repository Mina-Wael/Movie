package com.idyllic.movie.di

import android.app.Application
import androidx.room.Room
import com.idyllic.movie.data.localsource.MovieDao
import com.idyllic.movie.data.localsource.MovieDatabase
import com.idyllic.movie.data.remotesource.MovieApi
import com.idyllic.movie.data.repository.RepositoryImpl
import com.idyllic.movie.domain.repository.RepositoryIntr
import com.idyllic.movie.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module()
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    @Singleton
    fun provideApi(): MovieApi =
        Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(MovieApi::class.java)

    @Provides
    @Singleton
    fun provideRepo(api: MovieApi, dao: MovieDao): RepositoryIntr = RepositoryImpl(api, dao)

    @Provides
    @Singleton
    fun provideDao(context: Application): MovieDao =
        Room.databaseBuilder(context.applicationContext, MovieDatabase::class.java, "MovieDatabase")
            .build().getMovieDao()

}