package com.sangmeebee.searchmovie.model

import androidx.paging.PagingData

data class SearchMovieUiState(
    val movies: PagingData<MovieModel>? = null,
    val error: Throwable? = null,
)
