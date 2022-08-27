package com.sangmeebee.searchmovie.data.service

import com.sangmeebee.searchmovie.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieAPI {

    @GET("/")
    suspend fun getMovies(@Query("query") query: String): MovieResponse
}