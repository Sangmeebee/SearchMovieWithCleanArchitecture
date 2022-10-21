package com.sangmeebee.searchmovie.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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

    private val signInViewModel by viewModels<SignInViewModel>()

    override fun FragmentSigninBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = signInViewModel
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
        observeUser()
        observeError()
        observeIsLoading()
    }

    private fun observeUser() = repeatOnStarted {
        signInViewModel.userUiState.map { it.isLogin }.distinctUntilChanged()
            .collectLatest { isLogin ->
                if (isLogin) {
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToMyFragment())
                }
            }
    }

    private fun observeError() = repeatOnStarted {
        signInViewModel.userUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            error?.let {
                showToast(error.message)
                signInViewModel.fetchError(null)
            }
        }
    }

    private fun observeIsLoading() = repeatOnStarted {
        signInViewModel.userUiState.map { it.isLoading }.distinctUntilChanged()
            .collectLatest {
                binding.srlLoading.isRefreshing = it
            }
    }

    fun login(type: SocialType) = lifecycleScope.launch {
        signInViewModel.showLoading(true)
        socialLoginFactory(type).login(requireContext())
            .onSuccess { signInViewModel.fetchUser(type) }
            .onFailure {
                signInViewModel.fetchError(it)
                signInViewModel.showLoading(false)
            }
    }
}