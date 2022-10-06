package com.sangmeebee.searchmovie.model

data class SignInUiState(
    val doLogin: Boolean = false,
    val error: Throwable? = null,
)