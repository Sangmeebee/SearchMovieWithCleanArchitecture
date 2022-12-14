package com.sangmeebee.searchmovie.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>

    fun getBookmarkedMovies(userToken: String): Flow<List<BookmarkedMovie>>
    suspend fun bookmark(userToken: String, movie: BookmarkedMovie): Result<Unit>
    suspend fun unbookmark(userToken: String, movieId: String): Result<Unit>
}