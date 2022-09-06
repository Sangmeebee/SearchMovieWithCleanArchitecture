package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.MovieInfo
import com.sangmeebee.searchmovie.model.MovieModel

internal fun MovieInfo.toPresentation() =
    MovieModel(
        id = "$title$releaseDate$subtitle$director$actor",
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
        userRating = userRating.toFloat() / 2
    )

internal fun List<MovieInfo>.toPresentation() = map { it.toPresentation() }