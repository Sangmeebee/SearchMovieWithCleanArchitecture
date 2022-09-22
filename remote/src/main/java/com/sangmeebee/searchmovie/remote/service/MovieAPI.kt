package com.sangmeebee.searchmovie.remote.service

import com.sangmeebee.searchmovie.remote.datasource.MoviePagingSource.Companion.PAGE_DISPLAY_SIZE
import com.sangmeebee.searchmovie.remote.datasource.MoviePagingSource.Companion.STARTING_PAGE_INDEX
import com.sangmeebee.searchmovie.remote.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI {

    @GET("v1/search/movie.json")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("display") display: Int = PAGE_DISPLAY_SIZE,
        @Query("start") start: Int = STARTING_PAGE_INDEX,
    ): MovieResponse
}