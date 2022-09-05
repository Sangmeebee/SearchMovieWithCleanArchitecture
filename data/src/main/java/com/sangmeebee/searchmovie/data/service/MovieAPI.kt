package com.sangmeebee.searchmovie.data.service

import com.sangmeebee.searchmovie.data.datasource.remote.MoviePagingDataSource.Companion.PAGE_DISPLAY_SIZE
import com.sangmeebee.searchmovie.data.datasource.remote.MoviePagingDataSource.Companion.STARTING_PAGE_INDEX
import com.sangmeebee.searchmovie.data.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MovieAPI {

    @GET("v1/search/movie.json")
    suspend fun getMovies(
        @Query("query") query: String,
        @Query("display") display: Int = PAGE_DISPLAY_SIZE,
        @Query("start") start: Int = STARTING_PAGE_INDEX,
    ): MovieResponse
}