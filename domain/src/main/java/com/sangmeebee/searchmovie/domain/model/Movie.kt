package com.sangmeebee.searchmovie.domain.model

data class Movie(
    val offsetTime: String,
    val totalCount: Long,
    val pageStart: Int,
    val pageSize: Int,
    val items: List<MovieInfo>,
)