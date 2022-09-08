package com.sangmeebee.searchmovie.domain.repository

import androidx.paging.PagingData
import com.sangmeebee.searchmovie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(query: String): Flow<PagingData<Movie>>
}