package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieBookmarkLocalDataSource {
    fun getBookmarkedMovies(userToken: String): Flow<List<BookmarkedMovieEntity>>
    suspend fun bookmark(userToken: String, movie: BookmarkedMovieEntity): Result<Unit>
    suspend fun unbookmark(userToken: String, movieId: String): Result<Unit>
}