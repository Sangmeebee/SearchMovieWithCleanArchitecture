package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.user.UserApiClient
import com.sangmeebee.searchmovie.model.MyUiState
import com.sangmeebee.searchmovie.model.SignInUiState
import com.sangmeebee.searchmovie.util.getUserInfo
import com.sangmeebee.searchmovie.util.hasToken
import com.sangmeebee.searchmovie.util.logout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    var isReady: Boolean = false

    private val _myUiState = MutableStateFlow(MyUiState())
    val myUiState = _myUiState.asStateFlow()

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState = _signInUiState.asStateFlow()

    fun login() {
        _signInUiState.update { it.copy(doLogin = true) }
    }

    fun fetchUser() = viewModelScope.launch {
        if (UserApiClient.hasToken()) {
            UserApiClient.getUserInfo().onSuccess { user ->
                //TODO Room에 UserModel 정보 저장하는 로직 추가 , 저장 성공하면 myUiState의 user 업데이트
                _signInUiState.update { it.copy(doLogin = false) }
                _myUiState.update { it.copy(user = user) }
            }
        }
    }

    fun logout() = viewModelScope.launch {
        _myUiState.update { it.copy(isLoading = true) }
        UserApiClient.logout()
            .onSuccess {
                _myUiState.update { it.copy(user = null) }
            }
            .onFailure { throwable -> _myUiState.update { it.copy(error = throwable) } }
        _myUiState.update { it.copy(isLoading = false) }
    }


    fun signInShowErrorMessage(throwable: Throwable) {
        _signInUiState.update { it.copy(error = throwable) }
    }

    fun signInErrorMessageShown() {
        _signInUiState.update { it.copy(error = null) }
    }

    fun myErrorMessageShown() {
        _myUiState.update { it.copy(error = null) }
    }

    init {
        viewModelScope.launch {
            if (UserApiClient.hasToken()) {
                UserApiClient.getUserInfo().onSuccess { user ->
                    _myUiState.update { it.copy(user = user) }
                }
            }
            isReady = true
        }
    }
}