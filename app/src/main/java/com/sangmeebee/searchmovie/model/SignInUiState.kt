package com.sangmeebee.searchmovie.model

data class SignInUiState(
    val isLogin: Boolean = false,
    val doLogin: Boolean = false,
    val error: Throwable? = null,
)