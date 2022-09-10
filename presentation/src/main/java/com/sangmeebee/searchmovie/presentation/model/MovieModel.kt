package com.sangmeebee.searchmovie.presentation.model

data class MovieModel(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
    val contributor: String,
    val userRating: Float,
    var isBookmarked: Boolean = false,
    val bookmark: () -> Unit,
)