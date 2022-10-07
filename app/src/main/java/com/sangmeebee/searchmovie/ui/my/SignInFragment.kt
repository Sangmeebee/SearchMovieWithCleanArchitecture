package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kakao.sdk.user.UserApiClient
import com.sangmeebee.searchmovie.databinding.FragmentSigninBinding
import com.sangmeebee.searchmovie.ui.UserViewModel
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.login
import com.sangmeebee.searchmovie.util.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class SignInFragment :
    BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::inflate) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun FragmentSigninBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = userViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeError()
        observeDoLogin()
        observeIsLogin()
        setSwipeRefreshLayout()
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.isEnabled = false
    }


    private fun observeDoLogin() = repeatOnStarted {
        userViewModel.signInUiState.map { it.doLogin }.distinctUntilChanged()
            .collectLatest { doLogin ->
                binding.srlLoading.isRefreshing = doLogin
                if (doLogin) {
                    UserApiClient.login(requireContext())
                        .onSuccess { userViewModel.fetchUser() }
                        .onFailure { userViewModel.signInShowErrorMessage(it) }
                }
            }
    }

    private fun observeError() = repeatOnStarted {
        userViewModel.signInUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            error?.let {
                showToast(error.message)
                userViewModel.signInErrorMessageShown()
            }
        }
    }

    private fun observeIsLogin() = repeatOnStarted {
        userViewModel.signInUiState.map { it.isLogin }.distinctUntilChanged()
            .collectLatest { isLogin ->
                if (isLogin) {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMyFragment())
                }
            }
    }
}