package com.sangmeebee.searchmovie.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.sangmeebee.searchmovie.data.datasource.local.MovieBookmarkLocalDataSource
import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSource
import com.sangmeebee.searchmovie.data.model.MovieEntity
import com.sangmeebee.searchmovie.data.model.mapper.toData
import com.sangmeebee.searchmovie.data.model.mapper.toDomain
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieBookmarkLocalDataSource: MovieBookmarkLocalDataSource,
) : MovieRepository {
    override fun getMovies(query: String): Flow<PagingData<Movie>> =
        movieRemoteDataSource.getMovies(query).map { pagingData: PagingData<MovieEntity> ->
            pagingData.map { movie ->
                movie.toDomain()
            }
        }

    override suspend fun bookmark(movie: BookmarkedMovie): Result<Unit> =
        movieBookmarkLocalDataSource.bookmark(movie.toData())

    override suspend fun getAllBookmarked(): Result<List<BookmarkedMovie>> =
        movieBookmarkLocalDataSource.getAllBookmarked().map { bookmarkedMovie ->
            bookmarkedMovie.toDomain()
        }

    override suspend fun unbookmark(movieId: String): Result<Unit> =
        movieBookmarkLocalDataSource.unbookmark(movieId)
}