package com.sangmeebee.searchmovie.model

import com.kakao.sdk.user.model.AgeRange
import com.kakao.sdk.user.model.Gender

data class UserModel(
    val userId: Long,
    val nickname: String?,
    val profileImageUrl: String?,
    val email: String?,
    val gender: Gender?,
    val age: AgeRange?,
)