package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import com.sangmeebee.searchmovie.model.MovieModel

internal fun MovieModel.toDomain() = MovieBookmark(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)
