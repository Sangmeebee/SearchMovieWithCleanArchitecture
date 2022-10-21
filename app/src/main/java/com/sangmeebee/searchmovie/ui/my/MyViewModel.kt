package com.sangmeebee.searchmovie.ui.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.DeleteUserInfoUseCase
import com.sangmeebee.searchmovie.domain.usecase.DeleteUserTokenUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetCachedUserTokenUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetUserInfoUseCase
import com.sangmeebee.searchmovie.model.MyUiState
import com.sangmeebee.searchmovie.model.mapper.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    private val deleteUserTokenUseCase: DeleteUserTokenUseCase,
    getCachedUserTokenUseCase: GetCachedUserTokenUseCase,
) : ViewModel() {

    private val _userUiState = MutableStateFlow(MyUiState())
    val userUiState = _userUiState.asStateFlow()

    fun deleteUserToken() = viewModelScope.launch {
        deleteUserTokenUseCase()
            .onSuccess {
                _userUiState.update { it.copy(user = null, isLogin = false) }
            }
            .onFailure {
                showErrorMessage(it)
            }
    }

    fun showLoading(isLoading: Boolean) {
        _userUiState.update { it.copy(isLoading = isLoading) }
    }

    fun showErrorMessage(throwable: Throwable?) {
        _userUiState.update { it.copy(error = throwable) }
    }

    init {
        val userToken = getCachedUserTokenUseCase()
        if (userToken != null) {
            viewModelScope.launch {
                getUserInfoUseCase(userToken)
                    .onSuccess { user ->
                        _userUiState.update { it.copy(user = user.toPresentation()) }
                    }
                    .onFailure { showErrorMessage(it) }
            }
        }
    }
}