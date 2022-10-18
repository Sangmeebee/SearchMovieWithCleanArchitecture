package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity

interface MovieBookmarkLocalDataSource {
    suspend fun bookmark(movie: BookmarkedMovieEntity): Result<Unit>
    suspend fun getAllBookmarked(): Result<List<BookmarkedMovieEntity>>
    suspend fun unbookmark(movieId: String): Result<Unit>
}