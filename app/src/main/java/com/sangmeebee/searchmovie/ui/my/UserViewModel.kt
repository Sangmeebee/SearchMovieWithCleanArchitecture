package com.sangmeebee.searchmovie.ui.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.model.UserUiState
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
    // splash
    var isReady: Boolean = false
        private set

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState = _userUiState.asStateFlow()

    fun fetchUser(type: SocialType) = viewModelScope.launch {
        socialLoginFactory(type).getUserInfo()
            .onSuccess { user ->
                //TODO Room에 UserModel 정보 저장하는 로직 추가 , 저장 성공하면 myUiState의 user 업데이트
                _userUiState.update { it.copy(user = user, isLoading = false) }
            }
            .onFailure { throwable -> _userUiState.update { it.copy(error = throwable, isLoading = false) } }
    }

    fun removeUser() {
        //TODO Room에 저장되있는 User 정보 삭제 (userUiState.value.user)
        _userUiState.update { it.copy(user = null) }
    }

    fun showLoading(isLoading: Boolean) {
        _userUiState.update { it.copy(isLoading = isLoading) }
    }

    fun showErrorMessage(throwable: Throwable?) {
        _userUiState.update { it.copy(error = throwable) }
    }

    init {
        viewModelScope.launch {
            // TODO Room에서 데이터 가져와서 로그인 여부 확인하는 로직으로 변경
            val kakaoLoginUtil = socialLoginFactory(SocialType.KAKAO)
            if (kakaoLoginUtil.isLogin()) {
                kakaoLoginUtil.getUserInfo().onSuccess { user ->
                    _userUiState.update { it.copy(user = user) }
                }
            }
            isReady = true
        }
    }
}