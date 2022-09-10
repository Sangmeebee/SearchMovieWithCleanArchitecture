package com.sangmeebee.searchmovie.presentation.model.mapper

import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.presentation.model.MovieModel

internal fun Movie.toPresentation(
    isBookmarked: Boolean,
    bookmark: () -> Unit,
) =
    MovieModel(
        title = "${title}(${releaseDate})",
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        contributor = if (director.isEmpty()) {
            actor.removeSuffix("|")
        } else if (actor.isEmpty()) {
            "<b>${director.removeSuffix("|")}</b>"
        } else {
            "<b>${director.removeSuffix("|")}</b>|${actor.removeSuffix("|")}"
        },
        userRating = userRating.toFloat() / 2,
        isBookmarked = isBookmarked,
        bookmark = bookmark,
    )
