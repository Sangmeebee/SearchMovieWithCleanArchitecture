package com.sangmeebee.searchmovie.data.datasource.remote

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.data.model.MovieEntity
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getMovies(query: String): Flow<PagingData<MovieEntity>>
}