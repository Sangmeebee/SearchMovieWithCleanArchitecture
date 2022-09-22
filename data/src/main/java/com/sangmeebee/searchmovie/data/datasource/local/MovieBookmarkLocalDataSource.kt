package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity

interface MovieBookmarkLocalDataSource {
    suspend fun bookmark(movie: MovieBookmarkEntity): Result<Unit>
    suspend fun getAllBookmarked(): Result<List<MovieBookmarkEntity>>
    suspend fun unbookmark(movieId: String): Result<Unit>
}