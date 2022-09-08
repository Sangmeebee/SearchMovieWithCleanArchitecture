package com.sangmeebee.searchmovie.presentation.model

sealed class UIState<out T> {
    object Empty : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    class Success<T>(val data: T) : UIState<T>()
    class Error(val throwable: Throwable) : UIState<Nothing>()
}