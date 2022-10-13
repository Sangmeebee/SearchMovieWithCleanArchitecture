package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovie.databinding.FragmentSigninBinding
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.repeatOnStarted
import com.sangmeebee.searchmovie.util.social_login.SocialLoginFactory
import com.sangmeebee.searchmovie.util.social_login.SocialType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSigninBinding>(FragmentSigninBinding::inflate) {

    @Inject
    lateinit var socialLoginFactory: SocialLoginFactory

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun FragmentSigninBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = userViewModel
        signInFragment = this@SignInFragment
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
        userViewModel.userUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            error?.let {
                showToast(error.message)
                userViewModel.showErrorMessage(null)
            }
        }
    }

    private fun observeDoLogin() = repeatOnStarted {
        userViewModel.userUiState.map { it.isLoading }.distinctUntilChanged()
            .collectLatest {
                binding.srlLoading.isRefreshing = it
            }
    }

    private fun observeIsLogin() = repeatOnStarted {
        userViewModel.userUiState.map { it.user }.distinctUntilChanged()
            .collectLatest { user ->
                if (user != null) {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMyFragment())
                }
            }
    }

    fun login(type: SocialType) = lifecycleScope.launch {
        userViewModel.showLoading(true)
        socialLoginFactory(type).login(requireContext())
            .onSuccess { userViewModel.fetchUser(type) }
            .onFailure {
                userViewModel.showErrorMessage(it)
                userViewModel.showLoading(false)
            }
    }
}