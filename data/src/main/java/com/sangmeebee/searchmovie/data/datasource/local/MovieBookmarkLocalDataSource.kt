package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity

interface MovieBookmarkLocalDataSource {
    suspend fun bookmark(userToken: String, movie: BookmarkedMovieEntity): Result<Unit>
    suspend fun getAllBookmarked(userToken: String): Result<List<BookmarkedMovieEntity>>
    suspend fun unbookmark(userToken: String, movieId: String): Result<Unit>
}