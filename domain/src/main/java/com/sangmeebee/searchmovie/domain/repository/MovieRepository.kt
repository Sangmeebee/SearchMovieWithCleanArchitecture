package com.sangmeebee.searchmovie.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>
    suspend fun bookmark(movie: MovieBookmark)
    suspend fun getAllBookmarked(): List<MovieBookmark>
    suspend fun unbookmark(movie: MovieBookmark)
}