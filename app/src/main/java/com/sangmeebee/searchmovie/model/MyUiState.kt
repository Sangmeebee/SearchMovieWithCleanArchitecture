package com.sangmeebee.searchmovie.model

data class MyUiState(
    val isLoading: Boolean = false,
    val user: UserModel? = null,
    val error: Throwable? = null,
)
