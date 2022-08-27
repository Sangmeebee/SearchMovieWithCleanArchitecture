package com.sangmeebee.searchmovie.data.datasource.remote

import com.sangmeebee.searchmovie.domain.model.Movie

interface MovieRemoteDataSource {
    suspend fun getMovies(query: String): Result<Movie>
}