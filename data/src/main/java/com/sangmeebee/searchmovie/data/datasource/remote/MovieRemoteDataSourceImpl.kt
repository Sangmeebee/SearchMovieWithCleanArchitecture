package com.sangmeebee.searchmovie.data.datasource.remote

import com.sangmeebee.searchmovie.data.di.IoDispatcher
import com.sangmeebee.searchmovie.data.service.MovieAPI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieAPI: MovieAPI,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : MovieRemoteDataSource {
    override suspend fun getMovies(query: String) = runCatching {
        withContext(ioDispatcher) {
            movieAPI.getMovies(query).toDomain()
        }
    }
}