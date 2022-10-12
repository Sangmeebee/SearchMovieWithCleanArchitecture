package com.sangmeebee.searchmovie.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.model.MyUiState
import com.sangmeebee.searchmovie.model.SignInUiState
import com.sangmeebee.searchmovie.util.social_login.SocialLoginFactory
import com.sangmeebee.searchmovie.util.social_login.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val socialLoginFactory: SocialLoginFactory,
) : ViewModel() {
    var isReady: Boolean = false

    private val _myUiState = MutableStateFlow(MyUiState())
    val myUiState = _myUiState.asStateFlow()

    private val _signInUiState = MutableStateFlow(SignInUiState())
    val signInUiState = _signInUiState.asStateFlow()

    fun login(type: SocialType) {
        _signInUiState.update { it.copy(doLogin = type) }
    }

    fun fetchUser(type: SocialType) = viewModelScope.launch {
        socialLoginFactory.create(type).getUserInfo()
            .onSuccess { user ->
                //TODO Room에 UserModel 정보 저장하는 로직 추가 , 저장 성공하면 myUiState의 user 업데이트
                _signInUiState.update { it.copy(doLogin = null, isLogin = true) }
                _myUiState.update { it.copy(user = user) }
            }
            .onFailure { throwable -> _signInUiState.update { it.copy(error = throwable) } }
    }

    fun logout(type: SocialType) = viewModelScope.launch {
        _myUiState.update { it.copy(isLoading = true) }
        socialLoginFactory.create(type).logout()
            .onSuccess {
                _signInUiState.update { it.copy(isLogin = false) }
                _myUiState.update { it.copy(user = null) }
            }
            .onFailure { throwable -> _myUiState.update { it.copy(error = throwable) } }
        _myUiState.update { it.copy(isLoading = false) }
    }

    fun showErrorMessageInSignInFragment(throwable: Throwable) {
        _signInUiState.update { it.copy(doLogin = null, error = throwable) }
    }

    fun errorMessageShownInSignInFragment() {
        _signInUiState.update { it.copy(error = null) }
    }

    fun errorMessageShownInMyFragment() {
        _myUiState.update { it.copy(error = null) }
    }

    init {
        viewModelScope.launch {
            // TODO Room에서 데이터 가져와서 로그인 여부 확인하는 로직으로 변경
            val kakaoLoginUtil = socialLoginFactory.create(SocialType.KAKAO)
            if (kakaoLoginUtil.isLogin()) {
                kakaoLoginUtil.getUserInfo().onSuccess { user ->
                    _myUiState.update { it.copy(user = user) }
                    _signInUiState.update { it.copy(isLogin = true) }
                }
            }
            isReady = true
        }
    }
}