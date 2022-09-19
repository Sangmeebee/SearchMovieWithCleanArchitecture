package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import com.sangmeebee.searchmovie.model.MovieModel

internal fun Movie.toPresentation(
    isBookmarked: Boolean,
    bookmark: () -> Unit,
) = MovieModel(
    title = "${title}(${releaseDate})",
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = setMovieContributor(director, actor),
    userRating = setMovieRating(userRating),
    isBookmarked = isBookmarked,
    bookmark = bookmark,
)

internal fun Movie.toMovieBookmark() = MovieBookmark(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = setMovieContributor(director, actor),
    userRating = setMovieRating(userRating),
)

internal fun setMovieContributor(director: String, actor: String): String =
    if (director.isEmpty()) {
        actor.removeSuffix("|")
    } else if (actor.isEmpty()) {
        "<b>${director.removeSuffix("|")}</b>"
    } else {
        "<b>${director.removeSuffix("|")}</b>|${actor.removeSuffix("|")}"
    }

internal fun setMovieRating(rating: String) = rating.toFloat() / 2
