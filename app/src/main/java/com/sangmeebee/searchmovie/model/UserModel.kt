package com.sangmeebee.searchmovie.model

import com.sangmeebee.searchmovie.util.social_login.SocialType

data class UserModel(
    val userId: String,
    val nickname: String? = null,
    val profileImageUrl: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val age: String? = null,
    val loginType: SocialType? = null,
)