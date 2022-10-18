package com.sangmeebee.searchmovie.data.model

data class UserEntity(
    val userId: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val age: String? = null,
    val loginType: UserEntityLoginType,
)

enum class UserEntityLoginType {
    KAKAO, GOOGLE, NAVER
}