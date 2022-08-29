package com.sangmeebee.searchmovie.model

data class MovieModel(
    val offsetTime: String,
    val totalCount: Long,
    val pageStart: Long,
    val pageSize: Int,
    val items: List<MovieInfoModel>,
)

