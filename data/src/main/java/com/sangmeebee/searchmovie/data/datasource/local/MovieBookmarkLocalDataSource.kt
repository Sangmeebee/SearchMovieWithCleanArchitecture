package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.domain.model.MovieBookmark

interface MovieBookmarkLocalDataSource {
    suspend fun bookmark(movie: MovieBookmark): Result<Unit>
    suspend fun getAllBookmarked(): Result<List<MovieBookmark>>
    suspend fun unbookmark(movie: MovieBookmark): Result<Unit>
}