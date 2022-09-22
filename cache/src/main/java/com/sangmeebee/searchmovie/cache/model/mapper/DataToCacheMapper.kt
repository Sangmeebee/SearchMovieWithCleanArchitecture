package com.sangmeebee.searchmovie.cache.model.mapper

import com.sangmeebee.searchmovie.cache.model.MovieBookmarkPref
import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity

internal fun MovieBookmarkEntity.toPref() = MovieBookmarkPref(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)