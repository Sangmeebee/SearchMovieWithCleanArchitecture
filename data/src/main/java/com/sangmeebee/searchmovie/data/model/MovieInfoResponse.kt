package com.sangmeebee.searchmovie.data.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.Movie

@Entity
data class MovieInfoResponse(
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
) : DataToDomainMapper<Movie> {
    override fun toDomain(): Movie = Movie(
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
