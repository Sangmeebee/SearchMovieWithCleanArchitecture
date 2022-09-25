package com.sangmeebee.searchmovie.remote.model

import com.google.gson.annotations.SerializedName
import com.sangmeebee.searchmovie.data.model.MovieEntity
import com.sangmeebee.searchmovie.remote.model.mapper.RemoteToDataMapper

internal data class MovieInfoResponse(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("pubDate")
    val releaseDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
) : RemoteToDataMapper<MovieEntity> {
    override fun toData(): MovieEntity = MovieEntity(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        director = director,
        actor = actor,
        userRating = userRating
    )
}
