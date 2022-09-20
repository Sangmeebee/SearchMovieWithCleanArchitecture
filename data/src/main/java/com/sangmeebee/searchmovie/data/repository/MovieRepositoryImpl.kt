package com.sangmeebee.searchmovie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.datasource.remote.MoviePagingDataSource
import com.sangmeebee.searchmovie.data.datasource.remote.MoviePagingDataSource.Companion.PAGE_DISPLAY_SIZE
import com.sangmeebee.searchmovie.data.service.MovieAPI
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieAPI: MovieAPI,
    private val movieBookmarkLocalDataSource: MovieBookmarkLocalDataSource,
) : MovieRepository {
    override fun getMovies(query: String): Flow<PagingData<Movie>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_DISPLAY_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingDataSource(movieAPI, query) }
        ).flow

    override suspend fun bookmark(movie: MovieBookmark): Result<Unit> =
        movieBookmarkLocalDataSource.bookmark(movie)

    override suspend fun getAllBookmarked(): Result<List<MovieBookmark>> =
        movieBookmarkLocalDataSource.getAllBookmarked()

    override suspend fun unbookmark(movie: MovieBookmark): Result<Unit> =
        movieBookmarkLocalDataSource.unbookmark(movie)
}