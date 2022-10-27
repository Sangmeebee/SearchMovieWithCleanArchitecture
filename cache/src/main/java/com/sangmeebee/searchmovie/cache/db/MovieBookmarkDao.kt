package com.sangmeebee.searchmovie.cache.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sangmeebee.searchmovie.cache.model.BookmarkedMoviePref
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MovieBookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: BookmarkedMoviePref)

    suspend fun insertForUser(userToken: String, movie: BookmarkedMoviePref) {
        insert(movie.copy(userOwnerToken = userToken))
    }

    @Query("SELECT * FROM movie_bookmark WHERE user_owner_token = :userToken")
    fun getMovies(userToken: String): Flow<List<BookmarkedMoviePref>>

    @Query("DELETE FROM movie_bookmark WHERE movie_id = :movieId AND user_owner_token = :userToken")
    suspend fun deleteMovieBookmark(userToken: String, movieId: String)
}
