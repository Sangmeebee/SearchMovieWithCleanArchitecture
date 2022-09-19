package com.sangmeebee.searchmovie.data.datasource.local

import com.sangmeebee.searchmovie.data.db.MovieBookmarkDao
import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.model.mapper.toData
import com.sangmeebee.searchmovie.data.model.mapper.toDomain
import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieBookmarkLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieBookmarkDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieBookmarkLocalDataSource {
    override suspend fun bookmark(movie: MovieBookmark) = withContext(ioDispatcher) {
        movieDao.insert(movie.toData())
    }

    override suspend fun getAllBookmarked(): List<MovieBookmark> = withContext(ioDispatcher) {
        movieDao.getMovies().toDomain()
    }

    override suspend fun unbookmark(movie: MovieBookmark) = withContext(ioDispatcher) {
        movieDao.deleteMovieBookmark(movie.toData())
    }
}