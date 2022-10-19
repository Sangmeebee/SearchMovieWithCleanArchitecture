package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.Movie
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.model.User
import com.sangmeebee.searchmovie.domain.model.UserLoginType
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.UserModel
import com.sangmeebee.searchmovie.util.social_login.SocialType

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

internal fun setMovieContributor(director: String, actor: String): String =
    if (director.isEmpty()) {
        actor.removeSuffix("|")
    } else if (actor.isEmpty()) {
        "<b>${director.removeSuffix("|")}</b>"
    } else {
        "<b>${director.removeSuffix("|")}</b>|${actor.removeSuffix("|")}"
    }

internal fun setMovieRating(rating: String) = rating.toFloat() / 2


internal fun BookmarkedMovie.toPresentation() = MovieModel(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating,
    isBookmarked = true
)

internal fun List<BookmarkedMovie>.toPresentation() = map { it.toPresentation() }


internal fun User.toPresentation() = UserModel(
    userToken = userToken,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    email = email,
    gender = gender,
    age = age,
    loginType = when (loginType) {
        UserLoginType.KAKAO -> SocialType.KAKAO
        UserLoginType.GOOGLE -> SocialType.GOOGLE
        UserLoginType.NAVER -> SocialType.NAVER
    },
)