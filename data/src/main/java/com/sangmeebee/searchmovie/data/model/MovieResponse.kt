package com.sangmeebee.searchmovie.data.model

import com.google.gson.annotations.SerializedName

internal data class MovieResponse(
    @SerializedName("lastBuildDate")
    val lookupDate: String,
    @SerializedName("total")
    val totalCount: Long,
    @SerializedName("start")
    val pageStart: Long,
    @SerializedName("display")
    val pageSize: Int,
    @SerializedName("items")
    val items: List<MovieInfoResponse>,
)