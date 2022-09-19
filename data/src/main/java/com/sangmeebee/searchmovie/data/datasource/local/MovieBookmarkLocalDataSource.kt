package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.domain.model.MovieBookmark

interface MovieBookmarkLocalDataSource {
    suspend fun bookmark(movie: MovieBookmark)
    suspend fun getAllBookmarked(): List<MovieBookmark>
    suspend fun unbookmark(movie: MovieBookmark)
}