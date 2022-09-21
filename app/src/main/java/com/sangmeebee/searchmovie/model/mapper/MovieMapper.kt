package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.MovieBookmark
import com.sangmeebee.searchmovie.model.MovieModel

internal fun Movie.toPresentation(
    isBookmarked: Boolean,
) = MovieModel(
    title = "${title}(${releaseDate})",
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = setMovieContributor(director, actor),
    userRating = setMovieRating(userRating),
    isBookmarked = isBookmarked
)

internal fun MovieModel.toMovieBookmark() = MovieBookmark(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)

internal fun MovieBookmark.toPresentation() = MovieModel(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating,
    isBookmarked = true
)

internal fun List<MovieBookmark>.toPresentation() = map { it.toPresentation() }

internal fun setMovieContributor(director: String, actor: String): String =
    if (director.isEmpty()) {
        actor.removeSuffix("|")
    } else if (actor.isEmpty()) {
        "<b>${director.removeSuffix("|")}</b>"
    } else {
        "<b>${director.removeSuffix("|")}</b>|${actor.removeSuffix("|")}"
    }

internal fun setMovieRating(rating: String) = rating.toFloat() / 2
