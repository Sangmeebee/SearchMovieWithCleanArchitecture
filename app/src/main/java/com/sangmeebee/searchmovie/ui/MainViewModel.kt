package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.InitUserTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val initUserTokenUseCase: InitUserTokenUseCase,
) : ViewModel() {
    // splash
    var isReady: Boolean = false
        private set

    init {
        viewModelScope.launch {
            initUserTokenUseCase()
            isReady = true
        }
    }
}