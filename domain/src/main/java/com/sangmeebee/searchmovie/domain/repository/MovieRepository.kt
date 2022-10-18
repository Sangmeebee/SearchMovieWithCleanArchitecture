package com.sangmeebee.searchmovie.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>
    suspend fun bookmark(movie: BookmarkedMovie): Result<Unit>
    suspend fun getAllBookmarked(): Result<List<BookmarkedMovie>>
    suspend fun unbookmark(movieId: String): Result<Unit>
}