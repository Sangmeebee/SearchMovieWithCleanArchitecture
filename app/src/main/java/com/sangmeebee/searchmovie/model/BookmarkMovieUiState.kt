package com.sangmeebee.searchmovie.model

data class BookmarkMovieUiState(
    val error: Throwable? = null,
    val IsNeedToLogin: Boolean = false,
)
