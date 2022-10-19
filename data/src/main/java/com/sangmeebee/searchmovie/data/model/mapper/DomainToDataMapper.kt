package com.sangmeebee.searchmovie.data.model.mapper

import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import com.sangmeebee.searchmovie.data.model.UserEntity
import com.sangmeebee.searchmovie.data.model.UserEntityLoginType
import com.sangmeebee.searchmovie.domain.model.BookmarkedMovie
import com.sangmeebee.searchmovie.domain.model.User
import com.sangmeebee.searchmovie.domain.model.UserLoginType

internal fun BookmarkedMovie.toData(): BookmarkedMovieEntity = BookmarkedMovieEntity(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)

internal fun User.toData(): UserEntity = UserEntity(
    userToken = userToken,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    email = email,
    gender = gender,
    age = age,
    loginType = when (loginType) {
        UserLoginType.KAKAO -> UserEntityLoginType.KAKAO
        UserLoginType.GOOGLE -> UserEntityLoginType.GOOGLE
        UserLoginType.NAVER -> UserEntityLoginType.NAVER
    }
)