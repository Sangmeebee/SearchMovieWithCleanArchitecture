package com.sangmeebee.searchmovie.data.model

import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie

data class BookmarkedMovieEntity(
    val title: String,
    val subtitle: String? = null,
    val link: String,
    val imageUrl: String?,
    val contributor: String,
    val userRating: Float,
) : DataToDomainMapper<BookmarkedMovie> {
    override fun toDomain(): BookmarkedMovie = BookmarkedMovie(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = contributor,
        userRating = userRating
    )
}