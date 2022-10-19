package com.sangmeebee.searchmovie.data.model

import com.sangmeebee.searchmovie.data.model.mapper.DataToDomainMapper
import com.sangmeebee.searchmovie.domain.model.User
import com.sangmeebee.searchmovie.domain.model.UserLoginType

data class UserEntity(
    val userToken: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val age: String? = null,
    val loginType: UserEntityLoginType,
) : DataToDomainMapper<User> {
    override fun toDomain(): User = User(
        userToken = userToken,
        nickname = nickname,
        profileImageUrl = profileImageUrl,
        email = email,
        gender = gender,
        age = age,
        loginType = when (loginType) {
            UserEntityLoginType.KAKAO -> UserLoginType.KAKAO
            UserEntityLoginType.GOOGLE -> UserLoginType.GOOGLE
            UserEntityLoginType.NAVER -> UserLoginType.NAVER
        }
    )
}

enum class UserEntityLoginType {
    KAKAO, GOOGLE, NAVER
}