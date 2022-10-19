package com.sangmeebee.searchmovie.cache.db

import androidx.room.*
import com.sangmeebee.searchmovie.cache.model.BookmarkedMoviePref
import com.sangmeebee.searchmovie.cache.model.UserWithBookmarkedMoviesPref

@Dao
internal interface MovieBookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: BookmarkedMoviePref)

    suspend fun insertForUser(userToken: String, movie: BookmarkedMoviePref) {
        insert(movie.copy(userOwnerToken = userToken))
    }

    @Transaction
    @Query("SELECT * FROM user WHERE user_token = :userToken")
    suspend fun getUserWithBookmarkedMovies(userToken: String): UserWithBookmarkedMoviesPref

    suspend fun getMovies(userToken: String): List<BookmarkedMoviePref> =
        getUserWithBookmarkedMovies(userToken).bookmarkedMovies

    @Query("DELETE FROM movie_bookmark WHERE movie_id = :movieId AND user_owner_token = :userToken")
    suspend fun deleteMovieBookmark(userToken: String, movieId: String)
}
