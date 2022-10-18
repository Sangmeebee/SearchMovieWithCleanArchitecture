package com.sangmeebee.searchmovie.domain.model

data class User(
    val userId: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val age: String? = null,
    val loginType: UserLoginType,
)

enum class UserLoginType {
    KAKAO, GOOGLE, NAVER
}