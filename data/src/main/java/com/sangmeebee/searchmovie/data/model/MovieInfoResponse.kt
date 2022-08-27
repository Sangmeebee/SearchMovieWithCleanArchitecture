package com.sangmeebee.searchmovie.data.model

import com.google.gson.annotations.SerializedName

internal data class MovieInfoResponse(
    val title: String,
    val link: String,
    @SerializedName("image")
    val imageUrl: String?,
    @SerializedName("pubData")
    val releaseDate: String,
    val director: String,
    val actor: String,
    val userRating: String,
)
