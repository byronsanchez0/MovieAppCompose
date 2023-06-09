package com.example.movieappcompose.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieappcompose.entity.FavMovie

@Dao
interface FavMovieDao {
   @Query("SELECT * FROM FavMovie")
    fun getAll(): List<FavMovie>

    @Query("SELECT * FROM FavMovie WHERE userId LIKE :userId")
    fun getById(userId: Long): List<FavMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg favmovies: FavMovie)

    @Delete
    fun delete(favMovie: FavMovie)
}