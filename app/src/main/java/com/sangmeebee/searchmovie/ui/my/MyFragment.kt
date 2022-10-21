package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
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

    @Inject
    lateinit var socialLoginFactory: SocialLoginFactory

    private val myViewModel by viewModels<MyViewModel>()

    override fun FragmentMyBinding.setBinding() {
        lifecycleOwner = viewLifecycleOwner
        viewModel = myViewModel
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
        observeError()
        observeIsLogin()
        observeIsLoading()
    }

    private fun observeIsLogin() = repeatOnStarted {
        myViewModel.userUiState.map { it.isLogin }.distinctUntilChanged().collectLatest { isLogin ->
            if (!isLogin) {
                findNavController().navigate(MyFragmentDirections.actionMyFragmentToSignInFragment())
            }
        }
    }

    private fun observeError() = repeatOnStarted {
        myViewModel.userUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            if (error != null) {
                showToast(error.message)
                myViewModel.fetchError(null)
            }
        }
    }

    private fun observeIsLoading() = repeatOnStarted {
        myViewModel.userUiState.map { it.isLoading }.distinctUntilChanged().collectLatest {
            binding.srlLoading.isRefreshing = it
        }
    }

    fun logout(type: SocialType) = lifecycleScope.launch {
        myViewModel.showLoading(true)
        socialLoginFactory(type).logout(requireContext())
            .onSuccess {
                myViewModel.deleteUserToken()
            }
            .onFailure(myViewModel::fetchError)
        myViewModel.showLoading(false)
    }
}