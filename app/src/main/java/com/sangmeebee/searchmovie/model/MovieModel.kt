package com.sangmeebee.searchmovie.model

data class MovieModel(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
    val contributor: String,
    val userRating: Float,
    val isBookmarked: Boolean = false,
)