package com.sangmeebee.searchmovie.cache.datasource

import com.sangmeebee.searchmovie.cache.db.MovieBookmarkDao
import com.sangmeebee.searchmovie.cache.model.mapper.toData
import com.sangmeebee.searchmovie.cache.model.mapper.toPref
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieBookmarkLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieBookmarkDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieBookmarkLocalDataSource {

    private val _bookmarkedMovies = MutableStateFlow<List<BookmarkedMovieEntity>>(emptyList())
    override val bookmarkedMovies: StateFlow<List<BookmarkedMovieEntity>>
        get() = _bookmarkedMovies.asStateFlow()

    override suspend fun bookmark(userToken: String, movie: BookmarkedMovieEntity): Result<Unit> {
        return runCatching {
            withContext(ioDispatcher) {
                movieDao.insertForUser(userToken, movie.toPref())
                getBookmarkedMovies(userToken).onSuccess { _bookmarkedMovies.value = it }
            }
        }
    }

    override suspend fun unbookmark(userToken: String, movieId: String): Result<Unit> {
        return runCatching {
            withContext(ioDispatcher) {
                movieDao.deleteMovieBookmark(userToken, movieId)
                getBookmarkedMovies(userToken).onSuccess { _bookmarkedMovies.value = it }
            }
        }
    }

    override suspend fun fetchInitBookmarkedMovies(userToken: String?): Result<Unit> {
        return runCatching {
            withContext(ioDispatcher) {
                if (userToken != null) {
                    _bookmarkedMovies.value = movieDao.getMovies(userToken).toData()
                } else {
                    _bookmarkedMovies.value = emptyList()
                }
            }
        }
    }

    private suspend fun getBookmarkedMovies(userToken: String): Result<List<BookmarkedMovieEntity>> = runCatching {
        withContext(ioDispatcher) {
            movieDao.getMovies(userToken).toData()
        }
    }
}