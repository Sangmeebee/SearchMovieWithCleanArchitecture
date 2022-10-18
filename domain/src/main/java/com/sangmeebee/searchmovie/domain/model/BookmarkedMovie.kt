package com.sangmeebee.searchmovie.domain.model

data class BookmarkedMovie(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
    val contributor: String,
    val userRating: Float,
)