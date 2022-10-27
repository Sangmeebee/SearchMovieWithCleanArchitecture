package com.sangmeebee.searchmovie.cache.datasource

import com.sangmeebee.searchmovie.cache.db.MovieBookmarkDao
import com.sangmeebee.searchmovie.cache.model.mapper.toData
import com.sangmeebee.searchmovie.cache.model.mapper.toPref
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieBookmarkLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieBookmarkDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieBookmarkLocalDataSource {

    override fun getBookmarkedMovies(userToken: String): Flow<List<BookmarkedMovieEntity>> =
        movieDao.getMovies(userToken).map { it.toData() }


    override suspend fun bookmark(userToken: String, movie: BookmarkedMovieEntity): Result<Unit> {
        return runCatching {
            withContext(ioDispatcher) {
                movieDao.insertForUser(userToken, movie.toPref())
            }
        }
    }

    override suspend fun unbookmark(userToken: String, movieId: String): Result<Unit> {
        return runCatching {
            withContext(ioDispatcher) {
                movieDao.deleteMovieBookmark(userToken, movieId)
            }
        }
    }
}