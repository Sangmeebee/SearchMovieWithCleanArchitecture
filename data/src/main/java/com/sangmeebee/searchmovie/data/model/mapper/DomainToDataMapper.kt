package com.sangmeebee.searchmovie.data.model.mapper

import com.sangmeebee.searchmovie.data.model.MovieBookmarkEntity
import com.sangmeebee.searchmovie.domain.model.MovieBookmark

internal fun MovieBookmark.toData(): MovieBookmarkEntity =
    MovieBookmarkEntity(
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = contributor,
        userRating = userRating
    )
