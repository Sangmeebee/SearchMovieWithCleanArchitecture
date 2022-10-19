package com.sangmeebee.searchmovie.cache.model.mapper

import com.sangmeebee.searchmovie.cache.model.BookmarkedMoviePref
import com.sangmeebee.searchmovie.cache.model.UserPref
import com.sangmeebee.searchmovie.cache.model.UserPrefLoginType
import com.sangmeebee.searchmovie.data.model.BookmarkedMovieEntity
import com.sangmeebee.searchmovie.data.model.UserEntity
import com.sangmeebee.searchmovie.data.model.UserEntityLoginType

internal fun BookmarkedMovieEntity.toPref() = BookmarkedMoviePref(
    title = title,
    subtitle = subtitle,
    link = link,
    imageUrl = imageUrl,
    contributor = contributor,
    userRating = userRating
)

internal fun UserEntity.toPref() = UserPref(
    userToken = userToken,
    nickname = nickname,
    profileImageUrl = profileImageUrl,
    email = email,
    gender = gender,
    age = age,
    loginType = when (loginType) {
        UserEntityLoginType.KAKAO -> UserPrefLoginType.KAKAO
        UserEntityLoginType.GOOGLE -> UserPrefLoginType.GOOGLE
        UserEntityLoginType.NAVER -> UserPrefLoginType.NAVER
    }
)