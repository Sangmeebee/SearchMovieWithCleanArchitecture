package com.sangmeebee.searchmovie.data.repository

import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSource
import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.repository.MovieRepository
import javax.inject.Inject

internal class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
) : MovieRepository {
    override suspend fun getMovies(
        query: String,
        display: Int,
        start: Int,
    ): Result<Movie> = movieRemoteDataSource.getMovies(query, display, start)
}