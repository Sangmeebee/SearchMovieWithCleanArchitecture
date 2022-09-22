package com.sangmeebee.searchmovie.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sangmeebee.searchmovie.cache.model.MovieBookmarkPref

@Dao
interface MovieBookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: MovieBookmarkPref)

    @Query("SELECT * FROM movie_bookmark")
    suspend fun getMovies(): List<MovieBookmarkPref>

    @Query("DELETE FROM movie_bookmark WHERE movie_id = :movieId")
    suspend fun deleteMovieBookmark(movieId: String)
}
