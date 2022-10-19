package com.sangmeebee.searchmovie.ui.my

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sangmeebee.searchmovie.domain.usecase.*
import com.sangmeebee.searchmovie.model.UserUiState
import com.sangmeebee.searchmovie.model.mapper.toDomain
import com.sangmeebee.searchmovie.model.mapper.toPresentation
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
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val postUserInfoUseCase: PostUserInfoUseCase,
    private val deleteUserInfoUseCase: DeleteUserInfoUseCase,
    private val getInitUserTokenUseCase: GetInitUserTokenUseCase,
    private val postUserTokenUseCase: PostUserTokenUseCase,
    private val deleteUserTokenUseCase: DeleteUserTokenUseCase,
) : ViewModel() {
    // splash
    var isReady: Boolean = false
        private set

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState = _userUiState.asStateFlow()

    fun fetchUser(type: SocialType) = viewModelScope.launch {
        socialLoginFactory(type).getUserInfo()
            .onSuccess { user ->
                getUserInfoUseCase(user.userToken)
                    .onFailure {
                        postUserInfoUseCase(user.toDomain())
                    }
                postUserTokenUseCase(user.userToken)
                _userUiState.update { it.copy(user = user, isLoading = false) }
            }
            .onFailure {
                showErrorMessage(it)
                showLoading(false)
            }
    }

    fun deleteUserToken() = viewModelScope.launch {
        deleteUserTokenUseCase()
            .onSuccess {
                _userUiState.update { it.copy(user = null) }
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
        viewModelScope.launch {
            getInitUserTokenUseCase()
                .onSuccess { userToken ->
                    if (userToken.isNotEmpty()) {
                        getUserInfoUseCase(userToken)
                            .onSuccess { user ->
                                _userUiState.update { it.copy(user = user.toPresentation()) }
                            }
                            .onFailure {
                                showErrorMessage(it)
                            }
                    }
                }
                .onFailure {
                    showErrorMessage(it)
                }
            isReady = true

        }
    }
}