package com.idyllic.movie.data.localsource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.idyllic.movie.domain.model.MovieTable

@Database(entities = [MovieTable::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao

}