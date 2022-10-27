package com.sangmeebee.searchmovie.model

data class SearchMovieUiState(
    val error: Throwable? = null,
    val IsNeedToLogin: Boolean = false,
)
