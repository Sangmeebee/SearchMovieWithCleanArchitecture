package com.sangmeebee.searchmovie.data.datasource.local

interface MovieBookmarkLocalDataSource {
    suspend fun bookmark(movieId: String)
    suspend fun getAllBookmarked(): List<String>
    suspend fun unbookmark(movieId: String)
}