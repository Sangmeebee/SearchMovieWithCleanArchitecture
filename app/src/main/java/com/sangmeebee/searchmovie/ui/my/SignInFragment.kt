package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovie.databinding.FragmentSigninBinding
import com.sangmeebee.searchmovie.ui.UserViewModel
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.repeatOnStarted
import com.sangmeebee.searchmovie.util.social_login.SocialLoginFactory
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
        setUpObserveUiState()
        setSwipeRefreshLayout()
    }

    private fun setSwipeRefreshLayout() {
        binding.srlLoading.isEnabled = false
    }

    private fun setUpObserveUiState() {
        observeError()
        observeDoLogin()
        observeIsLogin()
    }

    private fun observeError() = repeatOnStarted {
        userViewModel.signInUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            error?.let {
                showToast(error.message)
                userViewModel.errorMessageShownInSignInFragment()
            }
        }
    }

    private fun observeDoLogin() = repeatOnStarted {
        userViewModel.signInUiState.map { it.doLogin }.distinctUntilChanged()
            .collectLatest { doLogin ->
                binding.srlLoading.isRefreshing = doLogin != null
                if (doLogin != null) {
                    SocialLoginFactory().create(doLogin).login(requireContext())
                        .onSuccess { userViewModel.fetchUser(doLogin) }
                        .onFailure { userViewModel.showErrorMessageInSignInFragment(it) }
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