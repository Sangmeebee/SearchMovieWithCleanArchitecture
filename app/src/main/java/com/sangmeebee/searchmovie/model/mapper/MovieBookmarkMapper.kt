package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import com.sangmeebee.searchmovie.model.MovieModel

internal fun MovieBookmark.toPresentation(
    isBookmarked: Boolean = true,
    bookmark: () -> Unit,
) = MovieModel(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating,
    isBookmarked = isBookmarked,
    bookmark = bookmark,
)

internal fun List<MovieBookmark>.toMovieId() = map { it.link }