package com.sangmeebee.searchmovie.remote.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("lastBuildDate")
    val offsetTime: String,
    @SerializedName("total")
    val totalCount: Long,
    @SerializedName("start")
    val pageStart: Int,
    @SerializedName("display")
    val pageSize: Int,
    @SerializedName("items")
    val items: List<MovieInfoResponse>,
)