package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.MovieInfo
import com.sangmeebee.searchmovie.model.MovieInfoModel
import com.sangmeebee.searchmovie.model.MovieModel

internal fun Movie.toPresentation() =
    MovieModel(
        offsetTime = offsetTime,
        totalCount = totalCount,
        pageStart = pageStart,
        pageSize = pageSize,
        items = items.toPresentation()
    )


internal fun MovieInfo.toPresentation() =
    MovieInfoModel(
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