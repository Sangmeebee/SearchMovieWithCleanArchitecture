package com.sangmeebee.searchmovie.model

data class UserUiState(
    val user: UserModel? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)
