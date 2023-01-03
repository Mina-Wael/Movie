package com.idyllic.movie.di

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
    fun provideRepo(api: MovieApi): RepositoryIntr = RepositoryImpl(api)

}