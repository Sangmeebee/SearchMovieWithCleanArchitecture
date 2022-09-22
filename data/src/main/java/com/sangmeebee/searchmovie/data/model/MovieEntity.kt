package com.sangmeebee.searchmovie.data.model

import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.Movie

data class MovieEntity(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
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
