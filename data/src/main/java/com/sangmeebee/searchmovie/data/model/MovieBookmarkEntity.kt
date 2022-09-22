package com.sangmeebee.searchmovie.data.model

import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.MovieBookmark

data class MovieBookmarkEntity(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
    val contributor: String,
    val userRating: Float,
) : DataToDomainMapper<MovieBookmark> {
    override fun toDomain(): MovieBookmark = MovieBookmark(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = contributor,
        userRating = userRating
    )
}