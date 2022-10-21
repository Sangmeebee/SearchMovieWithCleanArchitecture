package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.InitDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val initDataUseCase: InitDataUseCase,
) : ViewModel() {
    // splash
    var isReady: Boolean = false
        private set

    init {
        viewModelScope.launch {
            initDataUseCase()
            isReady = true
        }
    }
}