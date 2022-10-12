package com.sangmeebee.searchmovie.model

import com.sangmeebee.searchmovie.util.social_login.SocialType

data class SignInUiState(
    val isLogin: Boolean = false,
    val doLogin: SocialType? = null,
    val error: Throwable? = null,
)