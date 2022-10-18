package com.sangmeebee.searchmovie.data.model.mapper

import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie

internal fun BookmarkedMovie.toData(): BookmarkedMovieEntity = BookmarkedMovieEntity(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)