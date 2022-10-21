package com.sangmeebee.searchmovie.model

data class BookmarkMovieUiState(
    val bookmarkedMovies: List<MovieModel> = emptyList(),
    val error: Throwable? = null,
)
