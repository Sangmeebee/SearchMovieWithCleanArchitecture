package com.sangmeebee.searchmovie.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>
    suspend fun bookmark(movieId: String)
    suspend fun getAllBookmarked(): List<String>
    suspend fun unbookmark(movieId: String)
}