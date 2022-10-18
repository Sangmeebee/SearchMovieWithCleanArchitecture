package com.sangmeebee.searchmovie.cache.model.mapper

import com.sangmeebee.searchmovie.cache.model.BookmarkedMoviePref
import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity

internal fun BookmarkedMovieEntity.toPref() = BookmarkedMoviePref(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)