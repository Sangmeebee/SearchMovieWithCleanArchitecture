package com.sangmeebee.searchmovie.cache.datasource

import com.sangmeebee.searchmovie.cache.db.MovieBookmarkDao
import com.sangmeebee.searchmovie.cache.model.mapper.toData
import com.sangmeebee.searchmovie.cache.model.mapper.toPref
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieBookmarkLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieBookmarkDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieBookmarkLocalDataSource {
    override suspend fun bookmark(userToken: String, movie: BookmarkedMovieEntity) = runCatching {
        withContext(ioDispatcher) {
            movieDao.insertForUser(userToken, movie.toPref())
        }
    }

    override suspend fun getAllBookmarked(userToken: String): Result<List<BookmarkedMovieEntity>> = runCatching {
        withContext(ioDispatcher) {
            movieDao.getMovies(userToken).toData()
        }

        return Result.success(emptyList())
    }

    override suspend fun unbookmark(userToken: String, movieId: String) = runCatching {
        withContext(ioDispatcher) {
            movieDao.deleteMovieBookmark(userToken, movieId)
        }
    }
}