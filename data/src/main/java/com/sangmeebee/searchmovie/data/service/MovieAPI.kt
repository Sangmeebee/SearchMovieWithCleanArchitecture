package com.sangmeebee.searchmovie.data.service

import com.sangmeebee.searchmovie.data.model.MovieResponse
import retrofit2.http.GET

internal interface MovieAPI {

    @GET
    suspend fun getMovies(query: String): MovieResponse
}