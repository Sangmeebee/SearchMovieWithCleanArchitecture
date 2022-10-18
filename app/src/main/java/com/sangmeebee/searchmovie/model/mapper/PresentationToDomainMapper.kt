package com.sangmeebee.searchmovie.model.mapper

import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.model.User
import com.sangmeebee.searchmovie.domain.model.UserLoginType
import com.sangmeebee.searchmovie.model.MovieModel
import com.sangmeebee.searchmovie.model.UserModel
import com.sangmeebee.searchmovie.util.social_login.SocialType

internal fun MovieModel.toDomain() = BookmarkedMovie(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)

internal fun UserModel.toDomain() = User(
    userId = userId,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    email = email,
    gender = gender,
    age = age,
    loginType = when (loginType) {
        SocialType.KAKAO -> UserLoginType.KAKAO
        SocialType.GOOGLE -> UserLoginType.GOOGLE
        SocialType.NAVER -> UserLoginType.NAVER
    }
)
