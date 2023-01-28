package com.idyllic.movie.data.localsource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.Query
import com.idyllic.movie.domain.model.MovieTable

@Dao
interface MovieDao {

    @Insert(onConflict = IGNORE)
    suspend fun addMovie(movie: MovieTable)

    @Delete
    suspend fun remove(movie: MovieTable)

    @Query("select * from Movie")
    suspend fun getAllMovies(): List<MovieTable>


}