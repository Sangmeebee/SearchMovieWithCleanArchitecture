package com.sangmeebee.searchmovie.model

import com.kakao.sdk.user.model.AgeRange
import com.kakao.sdk.user.model.Gender
import com.sangmeebee.searchmovie.util.social_login.SocialType

data class UserModel(
    val userId: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val email: String? = null,
    val gender: Gender? = null,
    val age: AgeRange? = null,
    val loginType: SocialType? = null
)