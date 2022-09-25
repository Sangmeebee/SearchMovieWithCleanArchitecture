package com.sangmeebee.searchmovie.cache.datasource

import com.sangmeebee.searchmovie.cache.db.MovieBookmarkDao
import com.sangmeebee.searchmovie.cache.model.mapper.toData
import com.sangmeebee.searchmovie.cache.model.mapper.toPref
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieBookmarkLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieBookmarkDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieBookmarkLocalDataSource {
    override suspend fun bookmark(movie: MovieBookmarkEntity) = runCatching {
        withContext(ioDispatcher) {
            movieDao.insert(movie.toPref())
        }
    }

    override suspend fun getAllBookmarked(): Result<List<MovieBookmarkEntity>> = runCatching {
        withContext(ioDispatcher) {
            movieDao.getMovies().toData()
        }
    }

    override suspend fun unbookmark(movieId: String) = runCatching {
        withContext(ioDispatcher) {
            movieDao.deleteMovieBookmark(movieId)
        }
    }
}