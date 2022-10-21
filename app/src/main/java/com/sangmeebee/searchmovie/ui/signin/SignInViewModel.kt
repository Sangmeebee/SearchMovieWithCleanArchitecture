package com.sangmeebee.searchmovie.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.GetCachedUserTokenUseCase
import com.sangmeebee.searchmovie.domain.usecase.GetUserInfoUseCase
import com.sangmeebee.searchmovie.domain.usecase.PostUserInfoUseCase
import com.sangmeebee.searchmovie.domain.usecase.PostUserTokenUseCase
import com.sangmeebee.searchmovie.model.SignInUiState
import com.sangmeebee.searchmovie.model.mapper.toDomain
import com.sangmeebee.searchmovie.util.social_login.SocialLoginFactory
import com.sangmeebee.searchmovie.util.social_login.SocialType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val socialLoginFactory: SocialLoginFactory,
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postUserInfoUseCase: PostUserInfoUseCase,
    private val postUserTokenUseCase: PostUserTokenUseCase,
    getCachedUserTokenUseCase: GetCachedUserTokenUseCase,
) : ViewModel() {

    private val _userUiState = MutableStateFlow(SignInUiState())
    val userUiState = _userUiState.asStateFlow()

    fun fetchUser(type: SocialType) = viewModelScope.launch {
        socialLoginFactory(type).getUserInfo()
            .onSuccess { user ->
                getUserInfoUseCase(user.userToken)
                    .onFailure {
                        postUserInfoUseCase(user.toDomain())
                    }
                postUserTokenUseCase(user.userToken)
                    .onSuccess {
                        _userUiState.update { it.copy(isLogin = true) }
                    }
                    .onFailure {
                        fetchError(it)
                    }
            }
            .onFailure {
                fetchError(it)
            }
        showLoading(false)
    }


    fun showLoading(isLoading: Boolean) {
        _userUiState.update { it.copy(isLoading = isLoading) }
    }

    fun fetchError(throwable: Throwable?) {
        _userUiState.update { it.copy(error = throwable) }
    }

    init {
        val userToken = getCachedUserTokenUseCase()
        if (userToken != null) {
            _userUiState.update { it.copy(isLogin = true) }
        }
    }
}