package com.sangmeebee.searchmovie.model

data class MyUiState(
    val user: UserModel? = null,
    val isLogin: Boolean = true,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)
