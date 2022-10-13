package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovie.databinding.FragmentMyBinding
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
class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate) {

    private val userViewModel by activityViewModels<UserViewModel>()

    @Inject
    lateinit var socialLoginFactory: SocialLoginFactory

    override fun FragmentMyBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = userViewModel
        myFragment = this@MyFragment
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

    private fun observeIsLoading() = repeatOnStarted {
        userViewModel.userUiState.map { it.isLoading }.distinctUntilChanged().collectLatest {
            binding.srlLoading.isRefreshing = it
        }
    }

    private fun observeUser() = repeatOnStarted {
        userViewModel.userUiState.map { it.user }.distinctUntilChanged().collectLatest { user ->
            if (user == null) {
                findNavController().navigate(MyFragmentDirections.actionMyFragmentToSignInFragment())
            }
        }
    }

    private fun observeError() = repeatOnStarted {
        userViewModel.userUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            if (error != null) {
                showToast(error.message)
                userViewModel.showErrorMessage(null)
            }
        }
    }

    fun logout(type: SocialType) = lifecycleScope.launch {
        userViewModel.showLoading(true)
        socialLoginFactory(type).logout(requireContext())
            .onSuccess {
                userViewModel.removeUser()
            }
            .onFailure(userViewModel::showErrorMessage)
        userViewModel.showLoading(false)
    }
}