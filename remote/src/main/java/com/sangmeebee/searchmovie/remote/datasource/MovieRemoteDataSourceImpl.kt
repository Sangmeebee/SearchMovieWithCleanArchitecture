package com.sangmeebee.searchmovie.remote.datasource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.sangmeebee.searchmovie.data.datasource.remote.MovieRemoteDataSource
import com.sangmeebee.searchmovie.data.model.MovieEntity
import com.sangmeebee.searchmovie.remote.datasource.MoviePagingSource.Companion.PAGE_DISPLAY_SIZE
import com.sangmeebee.searchmovie.remote.service.MovieAPI
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieAPI: MovieAPI,
) : MovieRemoteDataSource {
    override fun getMovies(query: String): Flow<PagingData<MovieEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_DISPLAY_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviePagingSource(movieAPI, query) }
        ).flow
}