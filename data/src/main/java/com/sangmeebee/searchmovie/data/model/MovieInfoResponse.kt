package com.sangmeebee.searchmovie.data.model

import com.google.gson.annotations.SerializedName
import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.MovieInfo

internal data class MovieInfoResponse(
    val title: String,
    val link: String,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("pubDate")
    val releaseDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
) : DataToDomainMapper<MovieInfo> {
    override fun toDomain(): MovieInfo = MovieInfo(
        title = title,
        link = link,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        director = director,
        actor = actor,
        userRating = userRating
    )
}
