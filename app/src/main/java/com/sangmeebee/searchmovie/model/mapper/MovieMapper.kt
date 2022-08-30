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
        title = title,
        subtitle = subtitle,
        link = link,
        imageUrl = imageUrl,
        releaseDate = releaseDate,
        director = director,
        actor = actor,
        userRating = userRating
    )

internal fun List<MovieInfo>.toPresentation() = map { it.toPresentation() }