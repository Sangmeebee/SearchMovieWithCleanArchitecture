package com.sangmeebee.searchmovie.ui.my

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.sangmeebee.searchmovie.databinding.FragmentMyBinding
import com.sangmeebee.searchmovie.ui.UserViewModel
import com.sangmeebee.searchmovie.ui.base.BaseFragment
import com.sangmeebee.searchmovie.util.repeatOnStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MyFragment : BaseFragment<FragmentMyBinding>(FragmentMyBinding::inflate) {

    private val userViewModel by activityViewModels<UserViewModel>()

    override fun FragmentMyBinding.setBinding() {
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
        observeUser()
        observeError()
        observeIsLoading()
    }

    private fun observeIsLoading() = repeatOnStarted {
        userViewModel.myUiState.map { it.isLoading }.distinctUntilChanged().collectLatest {
            binding.srlLoading.isRefreshing = it
        }
    }

    private fun observeUser() = lifecycleScope.launch {
        userViewModel.myUiState.map { it.user }.distinctUntilChanged().collectLatest { user ->
            if (user == null) {
                findNavController().navigate(MyFragmentDirections.actionMyFragmentToSignInFragment())
            }
        }
    }

    private fun observeError() = lifecycleScope.launch {
        userViewModel.myUiState.map { it.error }.distinctUntilChanged().collectLatest { error ->
            if (error != null) {
                showToast(error.message)
                userViewModel.myErrorMessageShown()
            }
        }
    }
}