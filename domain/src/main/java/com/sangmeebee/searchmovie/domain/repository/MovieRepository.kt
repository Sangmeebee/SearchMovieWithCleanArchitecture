package com.sangmeebee.searchmovie.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>
    suspend fun bookmark(userToken: String, movie: BookmarkedMovie): Result<Unit>
    suspend fun getAllBookmarked(userToken: String): Result<List<BookmarkedMovie>>
    suspend fun unbookmark(userToken: String, movieId: String): Result<Unit>
}