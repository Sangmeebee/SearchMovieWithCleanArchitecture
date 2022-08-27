package com.sangmeebee.searchmovie.data.model

import com.google.gson.annotations.SerializedName
import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.data.model.mapper.toDomain
import com.sangmeebee.searchmovie.domain.model.Movie

internal data class MovieResponse(
    @SerializedName("lastBuildDate")
    val offsetTime: String,
    @SerializedName("total")
    val totalCount: Long,
    @SerializedName("start")
    val pageStart: Long,
    @SerializedName("display")
    val pageSize: Int,
    @SerializedName("items")
    val items: List<MovieInfoResponse>,
) : DataToDomainMapper<Movie> {
    override fun toDomain(): Movie =
        Movie(
            offsetTime = offsetTime,
            totalCount = totalCount,
            pageStart = pageStart,
            pageSize = pageSize,
            items = items.toDomain(),
        )
}