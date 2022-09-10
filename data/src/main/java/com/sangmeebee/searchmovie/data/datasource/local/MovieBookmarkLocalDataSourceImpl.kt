package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.db.MovieBookmarkDao
import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity
import com.sangmeebee.searchmovie.data.model.mapper.toDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieBookmarkLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieBookmarkDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieBookmarkLocalDataSource {

    override suspend fun bookmark(movieId: String) = withContext(ioDispatcher) {
        movieDao.insert(MovieBookmarkEntity(movieId))
    }

    override suspend fun getAllBookmarked(): List<String> = withContext(ioDispatcher) {
        movieDao.getMovies().toDomain()
    }

    override suspend fun unbookmark(movieId: String) = withContext(ioDispatcher) {
        movieDao.deleteMovieBookmark(MovieBookmarkEntity(movieId))
    }
}